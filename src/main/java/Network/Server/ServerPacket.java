package Network.Server;

import Network.Protocol.ProtocolType;
import java.io.*;
public class ServerPacket {

    public byte[] sendSineUpResult(byte answer,byte[] body) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(bao);
        int size  = 0;
        byte[] headerBytes = ProtocolType.getAnswerHeader(answer,size);
        ds.write(headerBytes);
        if(body != null) {
            ds.write(body);
        }
        return bao.toByteArray();
    }
}
