package Network.Server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
public class Protocol {

    public final static int HEADER_SIZE = 4;
    //TYPE
    public final static byte SINE_UP = 1;
    public final static byte LOGIN = 2;
    public final static byte REGISTER = 3;
    public final static byte ACCEPT = 4;
    public final static byte CORRECTION = 5;
    public final static byte INQUIRY = 6;

    //CODE
    //TYPE:1
    public final static byte REGISTER_INFO = 1;
    //TYPE:2
    public final static byte LOGIN_INFO = 1;
    //TYPE:3 , CLIENT
    public final static byte ORDER = 1;
    public final static byte REVIEW = 2;

    //TYPE:3 , OWNER
    public final static byte MENU_INSERT = 1;
    public final static byte STORE_INSERT = 2;
    public final static byte OPTION_INSERT = 3;
    public final static byte REPLY = 4;

    //TYPE:4 , CLIENT
    public final static byte CANCEL_ORDER = 1;

    //TYPE:4, OWNER
    public final static byte ACCEPT_ORDER = 1;

    //TYPE:4 , MANAGER
    public final static byte ACCEPT_STORE = 1;
    public final static byte ACCEPT_MENU = 2;
    public final static byte ACCEPT_OWNER = 3;

    //TYPE:5 , CLIENT
    public final static byte CHANGE_CLIENT_INFO = 1;

    //Type:5 , OWNER
    public final static byte CHANGE_OWNER_INFO = 1;
    public final static byte CHANGE_STORE_INFO = 2;

    //TYPE:5 , MANAGER
    public final static byte CHANGE_MANAGER_INFO = 1;

    //TYPE:6 , CLIENT
    public final static byte ORDER_LIST = 1;
    public final static byte STORE_LIST = 2;
    public final static byte MENU_LIST = 3;

    //TYPE:6 , OWNER
    public final static byte MYSTORE_LIST = 1;
    public final static byte MYMENU_LIST = 2;
    public final static byte MYORDER_LIST = 3;
    public final static byte MYTOTAL_LIST = 3;

    //TYPE:6 , MANAGER
    public final static byte ALL_STORE_LIST = 1;
    public final static byte ALL_MENU_LIST = 2;
    public final static byte INFO_LIST = 3;
    public final static byte TOTAL_LIST = 4;

    //AUTHORITY
    public final static byte ANONYMITY= 0;
    public final static byte CLIENT = 1;
    public final static byte OWNER = 2;
    public final static  byte MANAGER = 3;

    //ANSWER
    public final static byte DEFAULT = 0;
    public final static byte SUCCESS = 1;
    public final static byte ERROR = 2;

    public static byte[] getHeader(byte type, byte code, byte authority, byte answer, int size) throws  IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bao);

        dos.writeByte(type);
        dos.writeByte(code);
        dos.writeByte(authority);
        dos.writeByte(answer);
        dos.writeInt(size);

        return bao.toByteArray();
    }
}
