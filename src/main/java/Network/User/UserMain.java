package Network.User;

import java.io.*;
import java.net.*;

public class UserMain {
    public static void main(String args[]) {
        Socket cliSocket = null;
        String host = "";

        try {
            cliSocket = new Socket(host, 7777);
            DataOutputStream socketDOS = new DataOutputStream(cliSocket.getOutputStream());
            DataInputStream socketDIS = new DataInputStream(cliSocket.getInputStream());
            //while ( true ) {
                UserAPP app = new UserAPP(socketDOS, socketDIS);
                app.run();
            //}
        }
        catch (UnknownHostException e) {
            System.err.println("서버를 찾을 수 없습니다");
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                cliSocket.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
