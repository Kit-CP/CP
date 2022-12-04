package Network.Server;

import Network.Protocol.ProtocolType;

import javax.xml.crypto.Data;
import java.io.*;
public class ServerPacket {
    int size  = 0;
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    DataOutputStream ds = new DataOutputStream(bao);

    public byte[] sendFormat(byte answer, byte[] body) throws IOException {
        if(body != null) {
            size = body.length;
        }else {
            size = 0;
        }
        byte[] headerBytes = ProtocolType.getAnswerHeader(answer, size);
        ds.write(headerBytes);
        if(size > 0) {
            ds.write(body);
        }
        return bao.toByteArray();
    }
    public byte[] sendLoginResultFormat(byte authority, byte answer, byte[] body) throws IOException {
        if(body != null) {
            size = body.length;
        }else {
            size = 0;
        }
        byte[] headerBytes = ProtocolType.getLoginResultHeader(authority, answer, size);
        ds.write(headerBytes);
        if(size > 0) {
            ds.write(body);
        }
        return bao.toByteArray();

    }

    public void sendSignUpResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

    public void sendLoginResult(byte authority, byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendLoginResultFormat(authority, answer, body));
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

    public void sendOrderResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
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

    public void sendCancelOrderResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer, body));
        dos.flush();
    }

    public void sendJudgeOwnerResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dos.write(sendFormat(answer,body));
        dos.flush();
    }

}