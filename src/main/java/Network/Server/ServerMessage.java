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
import java.util.HashMap;
import java.util.Map;

@Setter
public class ServerMessage {
    private byte type, code, authority, answer;
    private byte[] body;
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

    public ServerMessage(DataInputStream dis) throws IOException{
        this.type = dis.readByte();
        this.code = dis.readByte();
        this.authority = dis.readByte();
        this.answer = dis.readByte();
        this.size = dis.readInt();

        if ( size > 0 ) {
            body = new byte[size];
            dis.read(body);
        }
        else {
            body = new byte[0];
        }
    }
    public void run(DataOutputStream dos) throws IOException {

        ByteArrayInputStream bai = new ByteArrayInputStream(body);
        DataInputStream dis = new DataInputStream(bai);

        if(type == ProtocolType.SINE_UP) {

            if (authority == ProtocolAuthority.CLIENT) { //회원가입 고객

                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO userDTO = UserDTO.readUserDTO(dis);
                    int userAuthority = userDTO.getAuthority();
                    if (userAuthority == 1) {
                        userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(userDAO.signUpClient(userDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendSignUpResult(answer, null, dos);
                    }
                }
            }
            if (authority == ProtocolAuthority.OWNER) { //회원가입 점주

                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO userDTO = UserDTO.readUserDTO(dis);
                    int userAuthority = userDTO.getAuthority();
                    if (userAuthority == 2) {
                        userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(userDAO.signUpStoreKeeper(userDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendSignUpResult(answer, null, dos);
                    }
                }
            }
            if (type == ProtocolType.LOGIN) {
                
                if (authority == ProtocolAuthority.ANONYMITY) {//default값으로 익명

                    if (code == ProtocolCode.LOGIN_INFO) {
                        UserDTO user = UserDTO.readUserDTO(dis);
                        userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        int authorityNumber = userDAO.signIn(user); //해당 DAO 함수 리턴으로 권한을 넘겨주게끔. 오류는 1,2,3이 아닌 모든 int 값
                        if(authorityNumber == 1) {
                            authority = ProtocolAuthority.CLIENT;
                            answer = ProtocolAnswer.SUCCESS;
                        }else if(authorityNumber == 2) {
                            authority = ProtocolAuthority.OWNER;
                            answer = ProtocolAnswer.SUCCESS;
                        }else if(authorityNumber == 3) {
                            authority = ProtocolAuthority.MANAGER;
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendLoginResult(authority, answer,null,dos);
                    }
                }
            }
            if (type == ProtocolType.REGISTER) { //등록
                
                if (authority == ProtocolAuthority.CLIENT) {

                    if (code == ProtocolCode.ORDER) {//주문 등록
                        OrderDTO orderDTO = OrderDTO.readOrderDTO(dis);
                        orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(orderDAO.makeOrder(orderDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendOrderResult(answer, null, dos);
                    }
                    if (code == ProtocolCode.REVIEW) { //고객 리뷰 등록
                        ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dis);
                        reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(reviewDAO.writeReview(reviewDTO)){
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendReviewResult(answer,null,dos);
                    }
                    
                } else if (authority == ProtocolAuthority.OWNER) { //점주

                    if (code == ProtocolCode.MENU_INSERT) { // 메뉴 등록

                    }
                    if (code == ProtocolCode.STORE_INSERT) { // 가게 등록
                        StoreDTO storeDTO = StoreDTO.readStoreDTO(dis);
                        storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(storeDAO.insertStore(storeDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendStoreInsertResult(answer, null, dos);
                    }
                    if (code == ProtocolCode.OPTION_INSERT) { // 옵션 등록

                    }
                    if (code == ProtocolCode.REPLY) { // 리뷰 답글 작성
                        ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dis);
                        reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(reviewDAO.writeReply(reviewDTO.getReply(), reviewDTO.getReview_id())) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendReviewReplyResult(answer, null, dos);
                    }
                }
            }
            if (type == ProtocolType.ACCEPT) { //승인
                
                if (authority == ProtocolAuthority.CLIENT) { //고객
                    
                    if (code == ProtocolCode.CANCEL_ORDER) { // 주문 취소
                        int order_id = dis.readInt();
                        orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(orderDAO.cancelOrder(order_id)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendCancelMenuResult(answer, null, dos);
                    }
                }
                if (authority == ProtocolAuthority.OWNER) { //점주
                    
                    if (code == ProtocolCode.ACCEPT_ORDER) { // 주문 승인 or 취소
                        String storeName = dis.readUTF();
                        int order_id = dis.readInt();
                        int state = dis.readInt();
                        orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(orderDAO.updateState(storeName, order_id, state)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendCancelOrderResult(answer, null, dos);
                    }
                }
                if (authority == ProtocolAuthority.MANAGER) { //관리자
                    
                    if (code == ProtocolCode.ACCEPT_STORE) { //관리자의 가게 승인
                        String storeName = dis.readUTF();
                        storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(storeDAO.acceptStore(storeName)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendJudgeStoreResult(answer, null, dos);

                    }
                    if (code == ProtocolCode.ACCEPT_MENU) { //관리자의 메뉴 승인
                        MenuDTO menuDTO = MenuDTO.readMenuDTO(dis);
                        Map<String, Object> map = new HashMap<>();
                        map.put("menu_name", menuDTO.getMenu_name());
                        map.put("state", menuDTO.getState());

                        menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        if(menuDAO.judgeMenu(map)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendJudgeMenuResult(answer, body, dos);

                    }
                    if (code == ProtocolCode.ACCEPT_OWNER) {//관리자의 점주 승인
                        UserDTO userDTO = UserDTO.readUserDTO(dis);
                        userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        Map<String, Object> map = new HashMap<>();
                        map.put("user_ID", userDTO.getUser_ID());
                        map.put("state", userDTO.getState());

                        if(userDAO.judgeStoreKeeper(map)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                        serverPacket.sendJudgeOwnerResult(answer, null, dos);
                    }
                }
            }
            if (type == ProtocolType.CORRECTION) { //수정
                if (authority == ProtocolAuthority.CLIENT) { //고객
                    
                    if (code == ProtocolCode.CHANGE_CLIENT_INFO) {//고객 정보 수정

                    }
                }
                if (authority == ProtocolAuthority.OWNER) {//점주
                    
                    if (code == ProtocolCode.CHANGE_OWNER_INFO) { //점주 정보 수정

                    }
                    if (code == ProtocolCode.CHANGE_STORE_INFO) {//가게 정보 수정

                    }
                }
                if (authority == ProtocolAuthority.MANAGER) { //관리자
                    
                    if (code == ProtocolCode.CHANGE_MANAGER_INFO) {//관리자 정보 수정

                    }
                }
            }
            if (type == ProtocolType.INQUIRY) {//조회
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

                    if (code == ProtocolCode.ALL_STORE_LIST) {//모든 가게 조회

                    }
                    if (code == ProtocolCode.ALL_MENU_LIST) {//모든 메뉴 조회

                    }
                    if (code == ProtocolCode.INFO_LIST) {//고객과 점주 정보 조회

                    }
                    if (code == ProtocolCode.TOTAL_LIST) {//매출 조회

                    }
                }
            }
        }
    }
}
