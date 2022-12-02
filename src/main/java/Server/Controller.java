package Server;

import java.io.IOException;

public class Controller {
    Protocol protocol;
    public void run(byte type, byte authority, byte code, byte answer,byte[] body) {
        if(type == protocol.SINE_UP) {
            if(authority == protocol.ANONYMITY) { //회원가입
                if(code == protocol.REGISTER_INFO) {
                    //직렬화,역직렬화 클래스 사용 body를 역직렬화해서 해당 DTO랑 연결
                    //결과로 나온 권한에 의해서 authority = ?;
                }
            }
        }
        if(type == protocol.LOGIN) {
            if(authority == protocol.CLIENT) {
                if(code == protocol.LOGIN_INFO) {
                    /*try{
                        
                        answer = protocol.CORRECT //작업 성공 시
                    }catch(IOException e) {
                        answer = protocol.ERROR //작업 실패 시
                    }*/
                }
            }
            if(authority == protocol.OWNER) {
                if(code == protocol.LOGIN_INFO) {

                }
            }
            if(authority == protocol.MANAGER) {
                if(code == protocol.LOGIN_INFO) {

                }
            }
        }
        if(type == protocol.REGISTER) { //등록
            if(authority == protocol.CLIENT) {
                if(code == protocol.ORDER) {

                }
                if(code == protocol.REVIEW) {

                }
            }else if(authority == protocol.OWNER) {
                if(code == protocol.MENU_INSERT) {

                }
                if(code == protocol.STORE_INSERT) {

                }
                if(code == protocol.OPTION_INSERT) {

                }
                if(code == protocol.REPLY){

                }
            }
        }
        if(type == protocol.ACCEPT) {
            if(authority == protocol.CLIENT) {
                if(code == protocol.CANCEL_ORDER) {

                }
            }
            if(authority == protocol.OWNER) {
                if(code == protocol.ACCEPT_ORDER) {

                }
            }
            if(authority == protocol.MANAGER) {
                if(code == protocol.ACCEPT_STORE) {

                }
                if(code == protocol.ACCEPT_MENU) {

                }
                if(code == protocol.ACCEPT_OWNER) {

                }
            }
        }
        if(type == protocol.CORRECTION) {
            if(authority == protocol.CLIENT) {
                if(code == protocol.CHANGE_CLIENT_INFO) {

                }
            }
            if(authority == protocol.OWNER) {
                if(code == protocol.CHANGE_OWNER_INFO) {

                }
                if(code == protocol.CHANGE_STORE_INFO) {

                }
            }
            if(authority == protocol.MANAGER) {
                if(code == protocol.CHANGE_MANAGER_INFO) {

                }
            }
        }
        if(type == protocol.INQUIRY) {
            if(authority == protocol.CLIENT) {
                if(code == protocol.ORDER_LIST) {

                }
                if(code == protocol.STORE_LIST) {

                }
                if(code == protocol.MENU_LIST) {

                }
            }
            if(authority == protocol.OWNER) {
                if(code == protocol.MYSTORE_LIST) {

                }
                if(code == protocol.MYMENU_LIST) {

                }
                if(code == protocol.MYORDER_LIST) {

                }
                if(code == protocol.MYTOTAL_LIST) {

                }
            }
            if(authority == protocol.MANAGER) {
                if(code == protocol.ALL_STORE_LIST) {

                }
                if(code == protocol.ALL_MENU_LIST) {

                }
                if(code == protocol.INFO_LIST) {

                }
                if(code == protocol.TOTAL_LIST) {

                }
            }
        }
    }
}
