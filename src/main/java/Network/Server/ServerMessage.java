package Network.Server;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.*;
import Database.persistence.dto.*;
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
                    int authorityNumber = userDAO.signIn(user);
                    if (authorityNumber == 1) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else if (authorityNumber == 2) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else if (authorityNumber == 3) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendLoginResult(authorityNumber, answer, body, dos);
                }
            }

        } else if (type == ProtocolType.REGISTER) { //등록

            if (authority == ProtocolAuthority.CLIENT) {

                if (code == ProtocolCode.ORDER) {//주문 등록
                    OrderDTO orderDTO = OrderDTO.readOrderDTO(dataInput);
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (orderDAO.makeOrder(orderDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendOrderResult(answer, body, dos);
                }
                if (code == ProtocolCode.REVIEW) { //고객 리뷰 등록
                    ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dataInput);
                    reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (reviewDAO.writeReview(reviewDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendReviewResult(answer, body, dos);
                }

            } else if (authority == ProtocolAuthority.OWNER) { //점주

                if (code == ProtocolCode.MENU_INSERT) { // 메뉴 등록
                        List<MenuDTO> dtos = new ArrayList<>();
                        int listSize = dataInput.readInt();
                        for(int i=0; i<listSize; i++) {

                        }
                }
                if (code == ProtocolCode.STORE_INSERT) { // 가게 등록
                    StoreDTO storeDTO = StoreDTO.readStoreDTO(dataInput);
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (storeDAO.insertStore(storeDTO)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendStoreInsertResult(answer, body, dos);
                }
                if (code == ProtocolCode.OPTION_INSERT) { // 옵션 등록

                }
                if (code == ProtocolCode.REPLY) { // 리뷰 답글 작성
                    ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dataInput);
                    reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (reviewDAO.writeReply(reviewDTO.getReply(), reviewDTO.getReview_id())) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendReviewReplyResult(answer, body, dos);
                }
            }

        } else if (type == ProtocolType.ACCEPT) { //승인

            if (authority == ProtocolAuthority.CLIENT) { //고객

                if (code == ProtocolCode.CANCEL_ORDER) { // 메뉴 주문 취소
                    int order_id = dataInput.readInt();
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (orderDAO.cancelOrder(order_id)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendCancelMenuResult(answer, body, dos);
                }
            }
            if (authority == ProtocolAuthority.OWNER) { //점주

                if (code == ProtocolCode.ACCEPT_ORDER) { // 주문 승인 or 취소
                    String storeName = dataInput.readUTF();
                    int order_id = dataInput.readInt();
                    int state = dataInput.readInt();
                    orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (orderDAO.updateState(order_id, storeName, state)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendCancelOrderResult(answer, body, dos);
                }
            }
            if (authority == ProtocolAuthority.MANAGER) { //관리자

                if (code == ProtocolCode.ACCEPT_STORE) { //관리자의 가게 승인
                    String storeName = dataInput.readUTF();
                    storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if (storeDAO.acceptStore(storeName)) {
                        answer = ProtocolAnswer.SUCCESS;
                    } else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendJudgeStoreResult(answer, body, dos);
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
                    serverPacket.sendJudgeMenuResult(answer, body, dos);
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
                    serverPacket.sendJudgeOwnerResult(answer, body, dos);
                }
            }

        } else if (type == ProtocolType.CORRECTION) { //수정

            if (authority == ProtocolAuthority.CLIENT) { //고객

                if (code == ProtocolCode.CHANGE_CLIENT_INFO) {//고객 정보 수정
                    UserDTO userDTO = UserDTO.readUserDTO(dataInput);
                    userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    Map<String, Object> map = new HashMap<>();
                    //원하는 정보만 들어갔을 때 수정되도록... 나중에 하기
                }
            }
            if (authority == ProtocolAuthority.OWNER) {//점주

                if (code == ProtocolCode.CHANGE_OWNER_INFO) { //점주 정보 수정

                }
                if (code == ProtocolCode.CHANGE_MENU_INFO) {//가게 메뉴 가격 수정

                }
                if (code == ProtocolCode.CHANGE_MENU_STOCK) {//가게 재료 수량 수정
                    String menuName = dataInput.readUTF();
                    int newStock = dataInput.readInt();
                    menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                    if(menuDAO.updateStock(menuName,newStock)) {
                        answer = ProtocolAnswer.SUCCESS;
                    }else {
                        answer = ProtocolAnswer.ERROR;
                    }
                    serverPacket.sendUpdateStockResult(answer, body, dos);
                }
            }
            if (authority == ProtocolAuthority.MANAGER) { //관리자

                if (code == ProtocolCode.CHANGE_MANAGER_INFO) {//관리자 정보 수정

                }
            }
        }

        else if (type == ProtocolType.INQUIRY) {//조회
            if (authority == ProtocolAuthority.CLIENT) {//고객

                if (code == ProtocolCode.ORDER_LIST) {//주문 내역 조회

                }
                if (code == ProtocolCode.STORE_LIST) {//가게 정보 조회

                }
                if (code == ProtocolCode.MENU_LIST) {//메뉴 조회

                }
                if (code == ProtocolCode.REVIEW_LIST) {//리뷰 조회

                }
            }
            if (authority == ProtocolAuthority.OWNER) {//점주

                if (code == ProtocolCode.MYSTORE_LIST) {//나의 가게 정보 조회

                }
                if (code == ProtocolCode.MYMENU_LIST) {//나의 가게 메뉴 조회

                }
                if (code == ProtocolCode.MYORDER_LIST) {//가게 주문 조회

                }
                if (code == ProtocolCode.MYTOTAL_LIST) {//통계 정보 조회

                }
            }
            if (authority == ProtocolAuthority.MANAGER) {//관리자

                if (code == ProtocolCode.TOTAL_LIST) {//매출 조회

                }

            }
        }
    }
}
