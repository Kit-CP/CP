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

    private MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private MenuHasOptionDAO menuHasOptionDAO = new MenuHasOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private OptionDAO optionDAO = new OptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private OrderedMenuDAO orderedMenuDAO = new OrderedMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private OrderedOptionDAO orderedOptionDAO = new OrderedOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private ReviewDAO reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private StoreDAO storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    private UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    public ServerMessage(byte type, byte code, byte authority, byte answer) {
        this.type = type;
        this.code = code;
        this.authority = authority;
        this.answer = answer;

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
                        if(userDAO.signUpClient(userDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendSineUpResult(answer, null, dos);
                    }
                }
            }
            if (authority == ProtocolAuthority.OWNER) { //회원가입 점주
                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO userDTO = UserDTO.readUserDTO(dis);
                    int userAuthority = userDTO.getAuthority();
                    if (userAuthority == 2) {
                        if(userDAO.signUpStoreKeeper(userDTO)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendSineUpResult(answer, null, dos);
                    }
                }
            }
            if (type == ProtocolType.LOGIN) {
                if (authority == ProtocolAuthority.CLIENT) { //로그인 고객

                    if (code == ProtocolCode.LOGIN_INFO) {
                        UserDTO user = UserDTO.readUserDTO(dis);
                        if(userDAO.signIn(user)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sednLoginResult(answer,null,dos);
                    }
                }
                if (authority == ProtocolAuthority.OWNER) { //로그인 점주

                    if (code == ProtocolCode.LOGIN_INFO) {
                        UserDTO user = UserDTO.readUserDTO(dis);
                        if(userDAO.signIn(user)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sednLoginResult(answer,null,dos);
                    }
                }
                if (authority == ProtocolAuthority.MANAGER) { // 로그인 관리자

                    if (code == ProtocolCode.LOGIN_INFO) {
                        UserDTO user = UserDTO.readUserDTO(dis);
                        if(userDAO.signIn(user)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sednLoginResult(answer,null,dos);
                    }
                }
            }
            if (type == ProtocolType.REGISTER) { //등록
                if (authority == ProtocolAuthority.CLIENT) {

                    if (code == ProtocolCode.ORDER) {

                    }
                    if (code == ProtocolCode.REVIEW) { //고객 리뷰 등록
                        ReviewDTO reviewDTO = ReviewDTO.readReviewDTO(dis);
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
                        if(orderDAO.cancelOrder(order_id)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }
                    }

                    serverPacket.sendCancelMenuResult(answer, null, dos);
                }
                if (authority == ProtocolAuthority.OWNER) { //점주
                    if (code == ProtocolCode.ACCEPT_ORDER) { // 주문 승인 or 취소

                    }
                }
                if (authority == ProtocolAuthority.MANAGER) { //관리자
                    if (code == ProtocolCode.ACCEPT_STORE) { //가게 승인
                        String storeName = dis.readUTF();
                        if(storeDAO.acceptStore(storeName)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendJudgeStoreResult(answer, null, dos);

                    }
                    if (code == ProtocolCode.ACCEPT_MENU) { //메뉴 승인
                        MenuDTO menuDTO = MenuDTO.readMenuDTO(dis);
                        Map<String, Object> map = new HashMap<>();
                        map.put("menu_name", menuDTO.getMenu_name());
                        map.put("state", menuDTO.getState());

                        if(menuDAO.judgeMenu(map)) {
                            answer = ProtocolAnswer.SUCCESS;
                        }else {
                            answer = ProtocolAnswer.ERROR;
                        }

                        serverPacket.sendJudgeMenuResult(answer, body, dos);

                    }
                    if (code == ProtocolCode.ACCEPT_OWNER) {//점주 승인

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
