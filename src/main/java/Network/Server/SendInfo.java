package Network.Server;

import Network.Protocol;
import java.io.*;
public class SendInfo {

    public byte[] sendSineUpResult(byte type, byte code,byte authority, byte answer,byte[] body) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(bao);
        int size  = 0;
        byte[] headerBytes = Protocol.getHeader(type,code,authority,answer,size);
        ds.write(headerBytes);

        return bao.toByteArray();
    }
}
