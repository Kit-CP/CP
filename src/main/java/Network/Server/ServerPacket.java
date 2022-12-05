package Network.Server;

import Network.Protocol.ProtocolType;

import java.io.*;
public class ServerPacket {
    int size  = 0;

    DataOutputStream dataOutPut;
    /*ByteArrayOutputStream bao = new ByteArrayOutputStream();
    DataOutputStream ds = new DataOutputStream(bao);*/

    public void sendFormat(byte answer, byte[] body) {
        try {
            if (body != null) {
                size = body.length;
            } else {
                size = 0;
            }
            byte[] headerBytes = ProtocolType.getAnswerHeader(answer, size);
            dataOutPut.write(headerBytes);
            if (size > 0) {
                dataOutPut.write(body);
            }

            dataOutPut.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendListFormat(byte answer, byte[] body) {
        try {
            if (body != null) {
                size = body.length;
            } else {
                size = 0;
            }
            byte[] headerBytes = ProtocolType.getAnswerHeader(answer, size);
            dataOutPut.write(headerBytes);
            if(size > 0) {
                dataOutPut.write(body);
            }

            dataOutPut.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSignUpResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendLoginResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendReviewResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }
    public void sendStoreInsertResult(byte answer, byte[] body, DataOutputStream dos) {
            dataOutPut = dos;
            sendFormat(answer, body);
    }

    public void sendOrderResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendReviewReplyResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendJudgeStoreResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendJudgeMenuResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendCancelMenuResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendCancelOrderResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendJudgeOwnerResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendUpdateStockResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendUpdateUserInfo(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendUpdateMenuPrice(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendOrderList(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendListFormat(answer, body);
    }

    public void sendAcceptedStoreList(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendListFormat(answer, body);
    }

    public void sendAcceptedMenuList(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendListFormat(answer, body);
    }

    public void sendReviewList(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendListFormat(answer, body);
    }

    public void sendInsertOptionResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendFormat(answer, body);
    }

    public void sendMyStoreListResult(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendListFormat(answer, body);
    }

    public void sendPendingOwnersList(byte answer, byte[] body, DataOutputStream dos) {
        dataOutPut = dos;
        sendListFormat(answer, body);
    }
}
