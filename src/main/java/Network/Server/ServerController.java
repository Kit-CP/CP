package Network.Server;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.UserDAO;
import Database.persistence.dto.*;
import Network.Protocol;
import lombok.Setter;

import java.io.*;
@Setter
public class ServerController {
    byte type, code, authority, answer;
    byte[] body;
    int size = 0;
    public ServerController(byte type, byte code, byte authority, byte answer) {
        this.type = type;
        this.code = code;
        this.authority = authority;
        this.answer = answer;

    }
    public void run(DataOutputStream dos) throws IOException {

        ByteArrayInputStream bai = new ByteArrayInputStream(body);
        DataInputStream dis = new DataInputStream(bai);

        if(type == Protocol.SINE_UP) {
            if (authority == Protocol.CLIENT) { //회원가입 고객
                if (code == Protocol.REGISTER_INFO) {
                    UserDTO user = UserDTO.readUserDTO(dis);
                    int temp = user.getAuthority();
                    if (temp == 1) {
                        UserDAO dao = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        dao.signUpClient(user); //해당 DTO 리턴을 받아서 answer 타입 결정
                        answer = Protocol.SUCCESS;
                        SendInfo sf = new SendInfo();
                        dos.write(sf.sendSineUpResult(type, code, authority, answer, null));
                        dos.flush();
                    }
                }
            }
            if (authority == Protocol.OWNER) { //회원가입 점주
                if (code == Protocol.REGISTER_INFO) {
                    UserDTO user = UserDTO.readUserDTO(dis);
                    int temp = user.getAuthority();
                    if (temp == 2) {
                        UserDAO dao = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        dao.signUpClient(user);
                        answer = Protocol.SUCCESS;
                        SendInfo sf = new SendInfo();
                        dos.write(sf.sendSineUpResult(type, code, authority, answer, null));
                        dos.flush();
                    }
                }
            }
            if (type == Protocol.LOGIN) {
                if (authority == Protocol.CLIENT) { //로그인 고객
                    if (code == Protocol.LOGIN_INFO) {
                    
                    }
                }
                if (authority == Protocol.OWNER) { //로그인 점주
                    if (code == Protocol.LOGIN_INFO) {

                    }
                }
                if (authority == Protocol.MANAGER) { // 로그인 관리자
                    if (code == Protocol.LOGIN_INFO) {

                    }
                }
            }
            if (type == Protocol.REGISTER) { //등록
                if (authority == Protocol.CLIENT) { //고객 리뷰 등록
                    if (code == Protocol.ORDER) {

                    }
                    if (code == Protocol.REVIEW) {

                    }
                } else if (authority == Protocol.OWNER) { //점주
                    if (code == Protocol.MENU_INSERT) { // 메뉴 등록

                    }
                    if (code == Protocol.STORE_INSERT) { // 가게 등록

                    }
                    if (code == Protocol.OPTION_INSERT) { // 옵션 등록

                    }
                    if (code == Protocol.REPLY) { // 리뷰 답글 작성

                    }
                }
            }
            if (type == Protocol.ACCEPT) { //승인
                if (authority == Protocol.CLIENT) { //고객
                    if (code == Protocol.CANCEL_ORDER) { // 주문 취소

                    }
                }
                if (authority == Protocol.OWNER) { //점주
                    if (code == Protocol.ACCEPT_ORDER) { // 주문 승인 or 취소

                    }
                }
                if (authority == Protocol.MANAGER) { //관리자
                    if (code == Protocol.ACCEPT_STORE) { //가게 승인

                    }
                    if (code == Protocol.ACCEPT_MENU) { //메뉴 승인

                    }
                    if (code == Protocol.ACCEPT_OWNER) {//점주 승인

                    }
                }
            }
            if (type == Protocol.CORRECTION) { //수정
                if (authority == Protocol.CLIENT) { //고객
                    if (code == Protocol.CHANGE_CLIENT_INFO) {//고객 정보 수정

                    }
                }
                if (authority == Protocol.OWNER) {//점주
                    if (code == Protocol.CHANGE_OWNER_INFO) { //점주 정보 수정

                    }
                    if (code == Protocol.CHANGE_STORE_INFO) {//가게 정보 수정

                    }
                }
                if (authority == Protocol.MANAGER) { //관리자
                    if (code == Protocol.CHANGE_MANAGER_INFO) {//관리자 정보 수정

                    }
                }
            }
            if (type == Protocol.INQUIRY) {//조회
                if (authority == Protocol.CLIENT) {//고객
                    if (code == Protocol.ORDER_LIST) {//주문 내역 조회

                    }
                    if (code == Protocol.STORE_LIST) {//가게 정보 조회

                    }
                    if (code == Protocol.MENU_LIST) {//메뉴 조회

                    }
                    if (code == Protocol.REVIEW_LIST) {//리뷰 조회

                    }
                }
                if (authority == Protocol.OWNER) {//점주
                    if (code == Protocol.MYSTORE_LIST) {//나의 가게 정보 조회

                    }
                    if (code == Protocol.MYMENU_LIST) {//나의 가게 메뉴 조회

                    }
                    if (code == Protocol.MYORDER_LIST) {//가게 주문 조회

                    }
                    if (code == Protocol.MYTOTAL_LIST) {//통계 정보 조회

                    }
                }
                if (authority == Protocol.MANAGER) {//관리자
                    if (code == Protocol.ALL_STORE_LIST) {//모든 가게 조회

                    }
                    if (code == Protocol.ALL_MENU_LIST) {//모든 메뉴 조회

                    }
                    if (code == Protocol.INFO_LIST) {//고객과 점주 정보 조회

                    }
                    if (code == Protocol.TOTAL_LIST) {//매출 조회

                    }
                }
            }
        }
    }
}
