package Network.Server;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.UserDAO;
import Database.persistence.dto.*;
import Network.Protocol.ProtocolAnswer;
import Network.Protocol.ProtocolAuthority;
import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolType;
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

        if(type == ProtocolType.SINE_UP) {
            if (authority == ProtocolAuthority.CLIENT) { //회원가입 고객
                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO user = UserDTO.readUserDTO(dis);
                    int temp = user.getAuthority();
                    if (temp == 1) {
                        UserDAO dao = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        dao.signUpClient(user);
                        answer = ProtocolAnswer.SUCCESS;
                        ServerPacket sf = new ServerPacket();
                        dos.write(sf.sendSineUpResult(answer, null));
                        dos.flush();
                    }
                }
            }
            if (authority == ProtocolAuthority.OWNER) { //회원가입 점주
                if (code == ProtocolCode.REGISTER_INFO) {
                    UserDTO user = UserDTO.readUserDTO(dis);
                    int temp = user.getAuthority();
                    if (temp == 2) {
                        UserDAO dao = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        dao.signUpClient(user);
                        answer = ProtocolAnswer.SUCCESS;
                        ServerPacket sf = new ServerPacket();
                        dos.write(sf.sendSineUpResult(answer, null));
                        dos.flush();
                    }
                }
            }
            if (type == ProtocolType.LOGIN) {
                if (authority == ProtocolAuthority.CLIENT) { //로그인 고객
                    if (code == ProtocolCode.LOGIN_INFO) {
                    
                    }
                }
                if (authority == ProtocolAuthority.OWNER) { //로그인 점주
                    if (code == ProtocolCode.LOGIN_INFO) {

                    }
                }
                if (authority == ProtocolAuthority.MANAGER) { // 로그인 관리자
                    if (code == ProtocolCode.LOGIN_INFO) {

                    }
                }
            }
            if (type == ProtocolType.REGISTER) { //등록
                if (authority == ProtocolAuthority.CLIENT) { //고객 리뷰 등록
                    if (code == ProtocolCode.ORDER) {

                    }
                    if (code == ProtocolCode.REVIEW) {

                    }
                } else if (authority == ProtocolAuthority.OWNER) { //점주
                    if (code == ProtocolCode.MENU_INSERT) { // 메뉴 등록

                    }
                    if (code == ProtocolCode.STORE_INSERT) { // 가게 등록

                    }
                    if (code == ProtocolCode.OPTION_INSERT) { // 옵션 등록

                    }
                    if (code == ProtocolCode.REPLY) { // 리뷰 답글 작성

                    }
                }
            }
            if (type == ProtocolType.ACCEPT) { //승인
                if (authority == ProtocolAuthority.CLIENT) { //고객
                    if (code == ProtocolCode.CANCEL_ORDER) { // 주문 취소

                    }
                }
                if (authority == ProtocolAuthority.OWNER) { //점주
                    if (code == ProtocolCode.ACCEPT_ORDER) { // 주문 승인 or 취소

                    }
                }
                if (authority == ProtocolAuthority.MANAGER) { //관리자
                    if (code == ProtocolCode.ACCEPT_STORE) { //가게 승인

                    }
                    if (code == ProtocolCode.ACCEPT_MENU) { //메뉴 승인

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
