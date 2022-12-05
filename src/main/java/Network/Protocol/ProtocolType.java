package Network.Protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
public class ProtocolType {

    public final static int HEADER_SIZE = 4;
    //TYPE
    public final static byte SIGNUP = 1;
    public final static byte LOGIN = 2;
    public final static byte REGISTER = 3;
    public final static byte ACCEPT = 4;
    public final static byte CORRECTION = 5;
    public final static byte INQUIRY = 6;


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

    public static byte[] getAnswerHeader(byte answer, int size) throws IOException {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bao);

        dos.writeByte(answer);
        dos.writeInt(size);

        return bao.toByteArray();
    }
    public static byte[] getLoginResultHeader(byte authority, byte answer, int size) throws IOException {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bao);

        dos.writeByte(authority);
        dos.write(answer);
        dos.writeInt(size);

        return bao.toByteArray();
    }
}
