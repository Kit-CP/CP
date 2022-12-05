package Network.Protocol;

public class ProtocolCode {
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
    public final static byte CHANGE_MENU_INFO = 2;
    public final static byte CHANGE_MENU_STOCK = 3;

    //TYPE:5 , MANAGER
    public final static byte CHANGE_MANAGER_INFO = 1;

    //TYPE:6 , CLIENT
    public final static byte ORDER_LIST = 1;
    public final static byte STORE_LIST = 2;
    public final static byte MENU_LIST = 3;
    public final static byte REVIEW_LIST = 4;

    //TYPE:6 , OWNER
    public final static byte MYSTORE_LIST = 1;
    public final static byte MYMENU_LIST = 2;
    public final static byte MYREVIEW_LIST = 3;
    public final static byte MYTOTAL_LIST = 4;

    //TYPE:6 , MANAGER
    public final static byte PENDING_OWNER_LIST = 1;
    public final static byte PENDING_STORE_LIST = 2;
    public final static byte PENDING_MENU_LIST = 3;
    public final static byte TOTAL_LIST = 4;
}
