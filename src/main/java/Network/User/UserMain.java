package Network.User;

import java.io.*;
import java.net.*;

public class UserMain {
    public static void main(String args[]) {
        Socket cliSocket = null;
        String host = "127.0.0.1";

        try {
            cliSocket = new Socket(host, 7777);
            DataOutputStream dos = new DataOutputStream(cliSocket.getOutputStream());
            DataInputStream dis = new DataInputStream(cliSocket.getInputStream());
            //while ( true ) {
                UserAPP app = new UserAPP(dos, dis);
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
