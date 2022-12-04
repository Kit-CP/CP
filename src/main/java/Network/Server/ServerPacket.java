package Network.Server;

import Network.Protocol.ProtocolType;
import java.io.*;
public class ServerPacket {
    int size  = 0;
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    DataOutputStream ds = new DataOutputStream(bao);

    public byte[] sendFormat(byte answer, byte[] body) throws IOException {
        if(body != null) {
            size = body.length;
        }else
            size = 0;
        byte[] headerBytes = ProtocolType.getAnswerHeader(answer,size);
        ds.write(headerBytes);
        if(size > 0) {
            ds.write(body);
        }
        return bao.toByteArray();
    }

    public void sendSineUpResult(byte answer,byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

    public void sednLoginResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

    public void sendReviewResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }
    public void sendStoreInsertResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer,body));
        dos.flush();
    }

    public void sendReviewReplyResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer,body));
        dos.flush();
    }

    public void sendJudgeStoreResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

    public void sendJudgeMenuResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

    public void sendCancelMenuResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

}
