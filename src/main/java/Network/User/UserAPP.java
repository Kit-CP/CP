package Network.User;

import Database.persistence.dto.*;
import Database.view.OptionView;
import Database.view.StoreView;
import Database.view.UserView;
import Network.Protocol.ProtocolAnswer;
import Network.Protocol.ProtocolAuthority;
import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolType;
import Util.State;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class UserAPP {
    Scanner input;
    DataInputStream dis;
    DataOutputStream dos;
    String user_ID;
    int authority;
    int state;
    UserPacket userPacket = null;
    UserMessage userMessage = null;

    public UserAPP(DataOutputStream dos, DataInputStream dis) throws IOException {
        this.dos = dos;
        this.dis = dis;
        input = new Scanner(System.in);
    }

    private void printUserInfor() {
        System.out.println("<<" + State.getAuthority(authority) + ">>\t아이디 : " + user_ID + "\t상태 : " + State.getState(state));
        return;
    }

    public void run() throws IOException {
        boolean isLogin = false;
        boolean isExit = false;

        while ( !isExit ) {
            while ( !isLogin && !isExit ) {
                int command = 0;

                try {
                    System.out.println(UserScreen.Start_SCREEN);
                    command = Integer.parseInt(input.nextLine());
                } catch (InputMismatchException e) {
                    input = new Scanner(System.in);
                    System.out.println(UserScreen.INPUT_ERROR);
                }

                switch (command) {
                    case 1:
                        if (login()) {
                            isLogin = true;
                        }
                        break;
                    case 2:
                        signUp();
                        break;
                    case 3:
                        isExit = true;
                        break;
                    default:
                        System.out.println(UserScreen.INPUT_ERROR);
                        break;
                }
            }

            while ( isLogin && !isExit ) {
                if ( this.authority == 1 ) {
                    isLogin = clientRun();
                }
                else if ( this.authority == 2 ) {
                    isLogin = ownerRun();
                }
                else if ( this.authority == 3 ) {
                    isLogin = managerRun();
                }
            }

        }
        System.out.println(UserScreen.EXIT);
    }

    /*=============================================== 시작 메뉴 ===============================================*/

    private void signUp() {
        System.out.println(UserScreen.SELECT_AUTHORITY);
        int newAuthority = Integer.parseInt(input.nextLine());
        byte authority = 0;
        if ( newAuthority == 1 ) {
            authority = ProtocolAuthority.CLIENT;
        }
        else if ( newAuthority == 2 ) {
            authority = ProtocolAuthority.OWNER;
        }
        else {
            System.out.println(UserScreen.INPUT_ERROR);
            return;
        }

        UserDTO dto = makeUserDTO(newAuthority);

        userPacket = new UserPacket(dos, ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, authority, ProtocolAnswer.DEFAULT);
        userPacket.sendUserDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveSignUpResult();
    }

    private UserDTO makeUserDTO(int authority) {
        UserDTO temp = new UserDTO();
        String str;
        System.out.println(UserScreen.ENTER_ID);  str = input.nextLine();  temp.setUser_ID(str);
        System.out.println(UserScreen.ENTER_PW);  str = input.nextLine();  temp.setUser_PW(str);
        System.out.println(UserScreen.ENTER_ADDRESS);  str = input.nextLine();  temp.setUser_address(str);
        System.out.println(UserScreen.ENTER_NAME);  str = input.nextLine();  temp.setUser_name(str);
        System.out.println(UserScreen.ENTER_PHONE);  str = input.nextLine();  temp.setUser_phone(str);
        System.out.println(UserScreen.ENTER_AGE);  str = input.nextLine();  temp.setAge(Integer.parseInt(str));
        temp.setAuthority(authority);
        temp.setState(0);
        return temp;
    }

    private boolean login() throws IOException {
        System.out.println(UserScreen.ENTER_ID);
        String id = input.nextLine();
        System.out.println(UserScreen.ENTER_PW);
        String pw = input.nextLine();
        UserDTO dto = new UserDTO(id, pw);

        userPacket = new UserPacket(dos, ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolAuthority.ANONYMITY, ProtocolAnswer.DEFAULT);
        userPacket.sendUserDTO(dto);

        userMessage = new UserMessage(dis);
        UserDTO resultDTO = userMessage.receiveLoginResult();
        if ( resultDTO != null ) {
            this.authority = resultDTO.getAuthority();
            this.user_ID = resultDTO.getUser_ID();
            this.state = resultDTO.getState();
            return true;
        }
        else {
            return false;
        }
    }

    /*=============================================== 고객 ===============================================*/

    private boolean clientRun() {
        boolean isRun = true;
        while ( isRun ) {
            int command = 0;

            try {
                printUserInfor();
                System.out.println(UserScreen.CLIENT_SCREEN);
                command = Integer.parseInt(input.nextLine());
            } catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }

            switch ( command ) {
                case 1:
                    showStore();
                    break;
                case 2:
                    //order();
                    break;
                case 3:
                    orderCancel();
                    break;
                case 4:
                    //orderedList();
                    break;
                case 5:
                    updateInfor();
                    break;
                case 6:
                    System.out.println(UserScreen.LOGOUT);
                    isRun = false;
                    break;
                default:
                    System.out.println(UserScreen.INPUT_ERROR);
            }


        }
        return false;
    }

    private void showStore() {
        userPacket = new UserPacket(dos, ProtocolType.INQUIRY, ProtocolCode.STORE_LIST, ProtocolAuthority.CLIENT, ProtocolAnswer.DEFAULT);
        userPacket.request();

        userMessage = new UserMessage(dis);
        StoreView.printAcceptedStore(userMessage.receiveStoreList());
    }

    private void orderCancel() { //찬진이가 함. 고객입장에서 주문취소
        OrderDTO dto = new OrderDTO();
        System.out.println(UserScreen.ENTER_ORDER_ID);
        int order_id = Integer.parseInt(input.nextLine());
        dto.setOrder_id(order_id);
        dto.setUser_ID(user_ID);
        userPacket = new UserPacket(dos, ProtocolType.ACCEPT, ProtocolCode.CANCEL_ORDER, ProtocolAuthority.CLIENT, ProtocolAnswer.DEFAULT);
        userPacket.sendOrderCancel(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveCancelOrderResult();
        System.out.println();
    }

    private void updateInfor() {
        UserDTO dto = new UserDTO();
        dto.setAuthority(this.authority);
        dto.setState(this.state);

        int command = 0;
        System.out.println(UserScreen.UPDATE_USER_INFOR_MENU);
        command = Integer.parseInt(input.nextLine());
        while ( command != 7 ) {
            System.out.println(UserScreen.ENTER_NEW_INFOR);
            String temp = input.nextLine();
            switch ( command ) {
                case 1:
                    dto.setUser_ID(temp);
                    break;
                case 2:
                    dto.setUser_PW(temp);
                    break;
                case 3:
                    dto.setUser_name(temp);
                    break;
                case 4:
                    dto.setUser_phone(temp);
                    break;
                case 5:
                    dto.setUser_address(temp);
                    break;
                case 6:
                    dto.setAge(Integer.parseInt(temp));
                    break;
            }
            System.out.println(UserScreen.UPDATE_USER_INFOR_MENU);
            command = Integer.parseInt(input.nextLine());
        }

        userPacket = new UserPacket(dos, ProtocolType.CORRECTION, ProtocolCode.CHANGE_CLIENT_INFO, ProtocolAuthority.CLIENT, ProtocolAnswer.DEFAULT);
        userPacket.sendNewUserDTO(this.user_ID, dto);

        userMessage = new UserMessage(dis);
        if ( userMessage.receiveUpdateInforResult() ) {
            if ( dto.getUser_ID() != "" ) {
                this.user_ID = dto.getUser_ID();
            }
        }

        /*private void getReviewList() {
            userPacket = new UserPacket(dos, ProtocolType.INQUIRY, ProtocolCode.REVIEW_LIST, ProtocolAuthority.CLIENT, ProtocolAnswer.DEFAULT);
            System.out.println("");
            userPacket.requestMyReview();
        }*/
    }

    /*=============================================== 점주 ===============================================*/

    private boolean ownerRun() throws IOException {
        boolean isRun = true;
        List<StoreDTO> myList;

        while ( isRun ) {
            int command = 0;

            try {
                printUserInfor();
                if ( this.state != 1 ) {
                    System.out.println("승인되지 않은 점주이므로 로그아웃 됩니다.\n");
                    return false;
                }
                System.out.println("나의 매장 정보");
                myList = getMyStore();
                StoreView.printMyStores(myList);
                System.out.println(UserScreen.OWNER_SCREEN);
                command = Integer.parseInt(input.nextLine());
            } catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }

            switch ( command ) {
                case 1:
                    insertStore();
                    break;
                case 2:
                    insertOption();
                    break;
                case 3:
                    insertMenu();
                    break;
                case 4:
                    //reviewList();
                    break;
                case 5:
                    //statisticsOwner();
                    break;
                case 6:
                    System.out.println(UserScreen.LOGOUT);
                    isRun = false;
                    break;
                default:
                    System.out.println(UserScreen.INPUT_ERROR);
            }


        }
        return false;
    }

    private List<StoreDTO> getMyStore() {
        userPacket = new UserPacket(dos, ProtocolType.INQUIRY, ProtocolCode.MYSTORE_LIST, ProtocolAuthority.OWNER, ProtocolAnswer.DEFAULT);
        userPacket.sendString(user_ID);

        userMessage = new UserMessage(dis);
        return userMessage.receiveStoreList();
    }

    private void insertStore() {
        StoreDTO dto = makeStoreDTO();

        userPacket = new UserPacket(dos, ProtocolType.REGISTER, ProtocolCode.STORE_INSERT, ProtocolAuthority.OWNER, ProtocolAnswer.DEFAULT);
        userPacket.sendStoreDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveInsertStoreResult();
    }

    private StoreDTO makeStoreDTO() {
        StoreDTO temp = new StoreDTO();
        String str;
        System.out.println(UserScreen.ENTER_NAME);  str = input.nextLine();  temp.setStore_name(str);
        System.out.println(UserScreen.ENTER_ADDRESS);  str = input.nextLine();  temp.setStore_address(str);
        System.out.println(UserScreen.ENTER_PHONE);  str = input.nextLine();  temp.setStore_phone(str);
        System.out.println(UserScreen.ENTER_INFORMATION); str = input.nextLine();  temp.setInformation(str);
        temp.setUser_ID(this.user_ID);
        temp.setIsAccept(0);
        return temp;
    }

    private void insertOption() {
        userPacket = new UserPacket(dos, ProtocolType.REGISTER, ProtocolCode.OPTION_INSERT, ProtocolAuthority.OWNER, ProtocolAnswer.DEFAULT);
        userPacket.sendOptionDTOList(makeOptionDTOList());

        userMessage = new UserMessage(dis);
        userMessage.receiveInsertOptionResult();
    }

    private List<OptionDTO> makeOptionDTOList() {
        System.out.println("옵션을 등록할 가게이름을 입력하세요.");
        String storeName = input.nextLine();
        System.out.println("등록할 옵션의 수를 입력하세요.");
        int cnt = Integer.parseInt(input.nextLine());

        List<OptionDTO> list = new ArrayList<>();
        for ( int i = 0; i < cnt; i++ ) {
            System.out.println(UserScreen.ENTER_NAME);
            String oName = input.nextLine();
            System.out.println(UserScreen.ENTER_PRICE);
            int price = Integer.parseInt(input.nextLine());
            OptionDTO dto = new OptionDTO(oName, price, storeName);
            list.add(dto);
        }
        return list;
    }

    private void getMyOption(String str) {
        userPacket = new UserPacket(dos, ProtocolType.INQUIRY, ProtocolCode.MYOPTION_LIST, ProtocolAuthority.OWNER, ProtocolAnswer.DEFAULT);
        userPacket.sendString(str);

        userMessage = new UserMessage(dis);
        OptionView.printNamePrice(userMessage.receiveOptionDTOList());
    }

    private void insertMenu() {
        System.out.println("메뉴를 등록할 가게이름을 입력하세요.");
        String storeName = input.nextLine();

        getMyOption(storeName);
        System.out.println("등록할 메뉴의 수를 입력하세요.");
        int cnt = Integer.parseInt(input.nextLine());

        List<MenuDTO> dtos = new ArrayList<>();
        List<String> strs = new ArrayList<>();

        for ( int i = 0; i < cnt; i++ ) {
            System.out.println("메뉴 카테고리를 입력하세요.");
            String cate = input.nextLine();
            System.out.println(UserScreen.ENTER_NAME);
            String name = input.nextLine();
            System.out.println(UserScreen.ENTER_PRICE);
            int price = Integer.parseInt(input.nextLine());
            System.out.println("재고를 입력하세요.");
            int stock = Integer.parseInt(input.nextLine());
            dtos.add(new MenuDTO(name, storeName, cate, price, stock));
            System.out.println("옵션을 입력하세요(구분자:/) (없으면 \"없음\"입력)");
            String temp = input.nextLine();
            if ( temp.equals("없음") ) {
                strs.add("");
            }
            else {
                strs.add(temp);
            }
        }

        userPacket = new UserPacket(dos, ProtocolType.REGISTER, ProtocolCode.MENU_INSERT, ProtocolAuthority.OWNER, ProtocolAnswer.DEFAULT);
        userPacket.sendMenuList(dtos, strs);

        userMessage = new UserMessage(dis);
        userMessage.receiveInsertResult();
    }

    /*=============================================== 관리자 ===============================================*/

    private boolean managerRun() {
        boolean isRun = true;
        while ( isRun ) {
            int command = 0;

            try {
                printUserInfor();
                System.out.println(UserScreen.MANAGER_SCREEN);
                command = Integer.parseInt(input.nextLine());
            } catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }

            switch ( command ) {
                case 1:
                    showPendingOwners();
                    selectOwner();
                    break;
                case 2:
                    showPendingStores();
                    selectStore();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println(UserScreen.LOGOUT);
                    isRun = false;
                    break;
                default:
                    System.out.println(UserScreen.INPUT_ERROR);
            }
        }
        return false;
    }

    private void showPendingOwners() {
        userPacket = new UserPacket(dos, ProtocolType.INQUIRY, ProtocolCode.PENDING_OWNER_LIST, ProtocolAuthority.MANAGER, ProtocolAnswer.DEFAULT);
        userPacket.request();

        userMessage = new UserMessage(dis);
        List<UserDTO> dtos = userMessage.receiveUserDTOList();
        if ( dtos != null ) {
            UserView.printAll(dtos);
        }
    }

    private void selectOwner() {
        System.out.println("상태를 바꿀 user_ID를 적으세요.");
        String id = input.nextLine();
        System.out.println(UserScreen.SELECT_STATE);
        int state = Integer.parseInt(input.nextLine());
        UserDTO dto = new UserDTO(id, state);

        userPacket = new UserPacket(dos, ProtocolType.ACCEPT, ProtocolCode.ACCEPT_OWNER, ProtocolAuthority.MANAGER, ProtocolAnswer.DEFAULT);
        userPacket.sendUserDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveUpdateInforResult();
    }

    private void showPendingStores() {
        userPacket = new UserPacket(dos, ProtocolType.INQUIRY, ProtocolCode.PENDING_STORE_LIST, ProtocolAuthority.MANAGER, ProtocolAnswer.DEFAULT);
        userPacket.request();

        userMessage = new UserMessage(dis);
        List<StoreDTO> dtos = userMessage.receiveStoreList();
        if ( dtos != null ) {
            StoreView.printMyStores(dtos);
        }
    }

    private void selectStore() {
        System.out.println("상태를 바꿀 가게 이름을 선택하세요.");
        String sname = input.nextLine();
        System.out.println(UserScreen.SELECT_STATE);
        int state = Integer.parseInt(input.nextLine());
        StoreDTO dto = new StoreDTO(sname, state);

        userPacket = new UserPacket(dos, ProtocolType.ACCEPT, ProtocolCode.ACCEPT_STORE, ProtocolAuthority.MANAGER, ProtocolAnswer.DEFAULT);
        userPacket.sendStoreDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveUpdateInforResult();
    }

}
