package Network.Server;

import Network.Protocol.ProtocolAnswer;
import Network.Protocol.ProtocolType;

import java.io.*;
public class ServerPacket {
    int size  = 0;

    DataOutputStream dataOutPut;
    /*ByteArrayOutputStream bao = new ByteArrayOutputStream();
    DataOutputStream ds = new DataOutputStream(bao);*/

    public void sendFormat(byte answer, byte[] body) throws IOException {
        if(body != null) {
            size = body.length;
        }else {
            size = 0;
        }
        byte[] headerBytes = ProtocolType.getAnswerHeader(answer, size);
        dataOutPut.write(headerBytes);
        if(size > 0) {
            dataOutPut.write(body);
        }

        dataOutPut.flush();
    }

    public void sendSignUpResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendLoginResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendReviewResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }
    public void sendStoreInsertResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendOrderResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendReviewReplyResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendJudgeStoreResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendJudgeMenuResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendCancelMenuResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendCancelOrderResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendJudgeOwnerResult(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendUpdateMenuPrice(byte answer, byte[] body, DataOutputStream dos) throws IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendUpdateStockResult(byte answer, byte[] body, DataOutputStream dos) throws  IOException {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

}
