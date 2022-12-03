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
            if(authority == Protocol.CLIENT) { //회원가입
                if(code == Protocol.REGISTER_INFO) {
                    UserDTO user = UserDTO.readUserDTO(dis);
                    System.out.println(user.toString());
                    int temp = user.getAuthority();
                    if ( temp == 1 ) {
                        UserDAO dao = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        dao.signUpClient(user);
                        answer = Protocol.SUCCESS;
                        SendInfo sf = new SendInfo();
                        dos.write(sf.sendSineUpResult(type,code,authority,answer,null));
                        dos.flush();
                    }
                }
            }
            if(authority == Protocol.OWNER) {
                if(code == Protocol.REGISTER_INFO) {

                }
            }
            if(authority == Protocol.MANAGER) {
                if(code == Protocol.REGISTER_INFO){

                }
            }
        }
        if(type == Protocol.LOGIN) {
            if(authority == Protocol.CLIENT) {
                if(code == Protocol.LOGIN_INFO) {
                    /*try{
                        
                        answer = protocol.CORRECT //작업 성공 시
                    }catch(IOException e) {
                        answer = protocol.ERROR //작업 실패 시
                    }*/
                }
            }
            if(authority == Protocol.OWNER) {
                if(code == Protocol.LOGIN_INFO) {

                }
            }
            if(authority == Protocol.MANAGER) {
                if(code == Protocol.LOGIN_INFO) {

                }
            }
        }
        if(type == Protocol.REGISTER) { //등록
            if(authority == Protocol.CLIENT) {
                if(code == Protocol.ORDER) {

                }
                if(code == Protocol.REVIEW) {

                }
            }else if(authority == Protocol.OWNER) {
                if(code == Protocol.MENU_INSERT) {

                }
                if(code == Protocol.STORE_INSERT) {

                }
                if(code == Protocol.OPTION_INSERT) {

                }
                if(code == Protocol.REPLY){

                }
            }
        }
        if(type == Protocol.ACCEPT) {
            if(authority == Protocol.CLIENT) {
                if(code == Protocol.CANCEL_ORDER) {

                }
            }
            if(authority == Protocol.OWNER) {
                if(code == Protocol.ACCEPT_ORDER) {

                }
            }
            if(authority == Protocol.MANAGER) {
                if(code == Protocol.ACCEPT_STORE) {

                }
                if(code == Protocol.ACCEPT_MENU) {

                }
                if(code == Protocol.ACCEPT_OWNER) {

                }
            }
        }
        if(type == Protocol.CORRECTION) {
            if(authority == Protocol.CLIENT) {
                if(code == Protocol.CHANGE_CLIENT_INFO) {

                }
            }
            if(authority == Protocol.OWNER) {
                if(code == Protocol.CHANGE_OWNER_INFO) {

                }
                if(code == Protocol.CHANGE_STORE_INFO) {

                }
            }
            if(authority == Protocol.MANAGER) {
                if(code == Protocol.CHANGE_MANAGER_INFO) {

                }
            }
        }
        if(type == Protocol.INQUIRY) {
            if(authority == Protocol.CLIENT) {
                if(code == Protocol.ORDER_LIST) {

                }
                if(code == Protocol.STORE_LIST) {

                }
                if(code == Protocol.MENU_LIST) {

                }
            }
            if(authority == Protocol.OWNER) {
                if(code == Protocol.MYSTORE_LIST) {

                }
                if(code == Protocol.MYMENU_LIST) {

                }
                if(code == Protocol.MYORDER_LIST) {

                }
                if(code == Protocol.MYTOTAL_LIST) {

                }
            }
            if(authority == Protocol.MANAGER) {
                if(code == Protocol.ALL_STORE_LIST) {

                }
                if(code == Protocol.ALL_MENU_LIST) {

                }
                if(code == Protocol.INFO_LIST) {

                }
                if(code == Protocol.TOTAL_LIST) {

                }
            }
        }
    }
}
