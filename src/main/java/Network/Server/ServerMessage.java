package Network.Server;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.*;
import Database.persistence.dto.*;
import Network.MyListSerializer;
import Network.Protocol.ProtocolAnswer;
import Network.Protocol.ProtocolAuthority;
import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolType;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class ServerMessage {
    private byte type, code, authority, answer;
    private byte[] body = null;
    private int size = 0;
    private ServerPacket serverPacket = new ServerPacket();
    private MenuDAO menuDAO = null;
    private MenuHasOptionDAO menuHasOptionDAO = null;
    private OptionDAO optionDAO = null;
    private OrderDAO orderDAO = null;
    private OrderedMenuDAO orderedMenuDAO = null;
    private OrderedOptionDAO orderedOptionDAO = null;
    private ReviewDAO reviewDAO = null;
    private StoreDAO storeDAO = null;
    private UserDAO userDAO = null;

    private DataInputStream dataInput;

    public ServerMessage(DataInputStream dis) throws IOException {
        this.type = dis.readByte();
        this.code = dis.readByte();
        this.authority = dis.readByte();
        this.answer = dis.readByte();
        this.size = dis.readInt();

        dataInput = dis;
    }

    public void run(DataOutputStream dos) throws IOException {

        if (type == ProtocolType.SIGNUP) {

            if (authority == ProtocolAuthority.CLIENT) { //회원가입 고객

                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    int userAuthority = userDTO.getAuthority();
                    if (userAuthority == 1) {
                        userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if (userDAO.signUpClient(userDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        } else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendSignUpResult(answer, body, dos);
                    }
                }
            }
            if (authority == ProtocolAuthority.OWNER) { //회원가입 점주

                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    int userAuthority = userDTO.getAuthority();
                    if (userAuthority == 2) {
                        userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if (userDAO.signUpStoreKeeper(userDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        } else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendSignUpResult(answer, null, dos);
                    }
                }
            }

        } else if (type == ProtocolType.LOGIN) { //로그인

            if (authority == ProtocolAuthority.ANONYMITY) {//default값으로 익명

                if (code == ProtocolCode.LOGIN_INFO) {
                    UserDTO user = UserDTO.readUserDTO(dataInput);  //여기서 두 번째 통신 때 에러남..
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    UserDTO replyDTO = userDAO.login(user);
                    if ( replyDTO != null ) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    if(answer == ProtocolAnswer.SUCCESS) {
                        body = replyDTO.getBytes();
                        serverPacket.sendLoginResult(answer, body, dos);
                    } else {
                        serverPacket.sendLoginResult(answer, null, dos);
                    }
                }
            }

        } else if (type == ProtocolType.REGISTER) { //등록

            if (authority == ProtocolAuthority.CLIENT) {

                if (code == ProtocolCode.ORDER) {//주문 등록
                    NewOrderDTO newOrderDTO = NewOrderDTO.readNewOrderDTO(dataInput);
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (orderDAO.makeOrder(newOrderDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendOrderResult(answer, null, dos);
                }
                if (code == ProtocolCode.REVIEW) { //고객 리뷰 등록
                    ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dataInput);
                    reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (reviewDAO.writeReview(reviewDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendReviewResult(answer, null, dos);
                }

            } else if (authority == ProtocolAuthority.OWNER) { //점주

                if (code == ProtocolCode.MENU_INSERT) { // 메뉴 등록
                    List<String> strList = new ArrayList<>();
                    List<MenuDTO> menuDTOList = new ArrayList<>();
                    int listSize = dataInput.readInt();
                    for(int i=0; i<listSize; i++) {
                        menuDTOList.add(MenuDTO.readMenuDTO(dataInput));
                        strList.add(dataInput.readUTF());
                    }
                    menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());

                    if(menuDAO.insertMenuAll(menuDTOList, strList)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendInsertMenuResult(answer, null, dos);

                }
                if (code == ProtocolCode.STORE_INSERT) { // 가게 등록
                    StoreDTO storeDTO = StoreDTO.readStoreDTO(dataInput);
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (storeDAO.insertStore(storeDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendStoreInsertResult(answer, null, dos);
                }
                if (code == ProtocolCode.OPTION_INSERT) { // 옵션 등록
                    List<OptionDTO> list = new ArrayList<>();
                    int listSize = dataInput.readInt();
                    for (int i = 0; i < listSize; i++) {
                        list.add(OptionDTO.readOptionDTO(dataInput));
                    }
                    optionDAO = new OptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());

                    if( optionDAO.insertOptionAll(list) ) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendInsertOptionResult(answer, null, dos);
                }
                if (code == ProtocolCode.REPLY) { // 리뷰 답글 작성
                    ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dataInput);
                    reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (reviewDAO.writeReply(reviewDTO.getReply(), reviewDTO.getReview_id())) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendReviewReplyResult(answer, null, dos);
                }
            }

        } else if (type == ProtocolType.ACCEPT) { //승인

            if (authority == ProtocolAuthority.CLIENT) { //고객

                if (code == ProtocolCode.CANCEL_ORDER) { // 메뉴 주문 취소
                    OrderDTO orderDTO = OrderDTO.readOrderDTO(dataInput);
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (orderDAO.cancelOrder(orderDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendCancelMenuResult(answer, null, dos);
                }
            }
            if (authority == ProtocolAuthority.OWNER) { //점주

                if (code == ProtocolCode.ACCEPT_ORDER) { // 주문 승인 or 취소
                    OrderDTO orderDTO = OrderDTO.readOrderDTO(dataInput);
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (orderDAO.updateState(orderDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendCancelOrderResult(answer, null, dos);
                }
            }
            if (authority == ProtocolAuthority.MANAGER) { //관리자

                if (code == ProtocolCode.ACCEPT_STORE) { //관리자의 가게 승인
                    StoreDTO storeDTO = StoreDTO.readStoreDTO(dataInput);
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    Map<String, Object> map = new HashMap<>();
                    map.put("store_name", storeDTO.getStore_name());
                    map.put("state", storeDTO.getIsAccept());
                    if (storeDAO.judgeStore(map)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendJudgeStoreResult(answer, null, dos);
                }
                if (code == ProtocolCode.ACCEPT_MENU) { //관리자의 메뉴 승인
                    MenuDTO menuDTO = MenuDTO.readMenuDTO(dataInput);
                    Map<String, Object> map = new HashMap<>();
                    map.put("menu_name", menuDTO.getMenu_name());
                    map.put("state", menuDTO.getState());

                    menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (menuDAO.judgeMenu(map)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendJudgeMenuResult(answer, null, dos);
                }
                if (code == ProtocolCode.ACCEPT_OWNER) {//관리자의 점주 승인
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    Map<String, Object> map = new HashMap<>();
                    map.put("user_ID", userDTO.getUser_ID());
                    map.put("state", userDTO.getState());

                    if (userDAO.judgeStoreKeeper(map)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendJudgeOwnerResult(answer, null, dos);
                }

            }

        } else if (type == ProtocolType.CORRECTION) { //수정

            if (authority == ProtocolAuthority.CLIENT) { //고객

                if (code == ProtocolCode.CHANGE_CLIENT_INFO) {//고객 정보 수정
                    String curID = dataInput.readUTF();
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (userDAO.updateInfor(curID, userDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendUpdateUserInfo(answer, null, dos);
                }
            }
            if (authority == ProtocolAuthority.OWNER) {//점주

                if (code == ProtocolCode.CHANGE_OWNER_INFO) { //점주 정보 수정
                    String newID = dataInput.readUTF();
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (userDAO.updateInfor(newID, userDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendUpdateUserInfo(answer, null, dos);
                }
                if (code == ProtocolCode.CHANGE_MENU_INFO) {//가게 메뉴 가격 수정
                    String name = dataInput.readUTF();
                    int newPrice = dataInput.readInt();
                    menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (menuDAO.updateMenu(name, newPrice)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendUpdateMenuPrice(answer, null, dos);
                }
                if (code == ProtocolCode.CHANGE_MENU_STOCK) {//가게 재료 수량 수정
                    String menuName = dataInput.readUTF();
                    int newStock = dataInput.readInt();
                    menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if(menuDAO.updateStock(menuName, newStock)) {
                        answer = ProtocolAnswer.SUCCESS;
                    }else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendUpdateStockResult(answer, null, dos);
                }
            }
            if (authority == ProtocolAuthority.MANAGER) { //관리자

                if (code == ProtocolCode.CHANGE_MANAGER_INFO) {//관리자 정보 수정
                    String newID = dataInput.readUTF();
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (userDAO.updateInfor(newID, userDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }

                    serverPacket.sendUpdateUserInfo(answer, null, dos);
                }
            }
        }

        else if (type == ProtocolType.INQUIRY) {//조회

            if (authority == ProtocolAuthority.CLIENT) {//고객

                if (code == ProtocolCode.ORDER_LIST) {//주문 내역 조회
                    String storeName = dataInput.readUTF();//가게 이름 받기
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<OrderViewDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(orderDAO.getOrderList(storeName));
                    if(body != null) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if(size != 0) {
                        serverPacket.sendOrderList(answer, body, dos);
                    } else {
                        serverPacket.sendOrderList(answer, null, dos);
                    }
                }
                if (code == ProtocolCode.STORE_LIST) {//승인된 가게 정보 조회
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<StoreDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(storeDAO.showAcceptedStore());

                    if(body != null) {
                        answer = ProtocolAnswer.SUCCESS;
                        size = body.length;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if (size != 0) {
                        serverPacket.sendAcceptedStoreList(answer, body, dos);
                    } else {
                        serverPacket.sendAcceptedStoreList(answer, null, dos);
                    }
                }
                if (code == ProtocolCode.MENU_LIST) {//메뉴 조회
                    menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<MenuDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(menuDAO.showAcceptedMenu());
                    if(body != null) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if (size != 0) {
                        serverPacket.sendAcceptedMenuList(answer, body, dos);
                    } else {
                        serverPacket.sendAcceptedMenuList(answer, null, dos);
                    }
                }
                if (code == ProtocolCode.REVIEW_LIST) {//리뷰 조회
                    String user_id = dataInput.readUTF(); //유저 아이디
                    String storeName = dataInput.readUTF(); //가게 이름
                    int crtPage = dataInput.readInt(); //보고싶은 페이지
                    reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<ReviewDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(reviewDAO.showReview(user_id, storeName, crtPage));
                    if(body != null) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if (size != 0) {
                        serverPacket.sendReviewList(answer, body, dos);
                    } else {
                        serverPacket.sendReviewList(answer, null, dos);
                    }
                }
            }
            if (authority == ProtocolAuthority.OWNER) {//점주

                if (code == ProtocolCode.MYSTORE_LIST) {//나의 가게 정보 조회
                    String owner_id = dataInput.readUTF();
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<StoreDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(storeDAO.getMyStoreList(owner_id));

                    if(body != null) {
                        size = body.length;
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if (size != 0) {
                        serverPacket.sendMyStoreListResult(answer, body, dos);
                    } else {
                        serverPacket.sendMyStoreListResult(answer, null, dos);
                    }

                }
                if (code == ProtocolCode.MYOPTION_LIST) {//나의 메뉴 옵션 조회

                }
                if (code == ProtocolCode.REVIEW_LIST) {//나의 가게 리뷰 조회

                }
                if (code == ProtocolCode.MYTOTAL_LIST) {//통계 정보 조회

                }
            }
            if (authority == ProtocolAuthority.MANAGER) {//관리자

                if (code == ProtocolCode.PENDING_OWNER_LIST) { //미승인된 점주 리스트
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<UserDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(userDAO.getPendingStoreKeepers());
                    size = body.length;

                    if(body != null) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if (size != 0) {
                        serverPacket.sendPendingOwnersList(answer, body, dos);
                    } else {
                        serverPacket.sendPendingOwnersList(answer, null, dos);
                    }
                }
                if (code == ProtocolCode.PENDING_STORE_LIST) { //미승인된 가게 리스트
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    MyListSerializer<StoreDTO> dtos = new MyListSerializer<>();
                    body = dtos.listToByte(storeDAO.showPendingStore());
                    size = body.length;

                    if(body != null) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    if (size != 0) {
                        serverPacket.sendPendingStoreList(answer, body, dos);
                    } else {
                        serverPacket.sendPendingStoreList(answer, null, dos);
                    }
                }
                if (code == ProtocolCode.PENDING_MENU_LIST) { //미승인된 메뉴 리스트

                }
                if (code == ProtocolCode.TOTAL_LIST) {//매출 조회

                }

            }
        }
    }
}
