package Network;

import Database.persistence.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

public class Packet { //메시지를 직렬화
    byte type, code, authority, answer;
    int size = 0;
    public Packet(byte type, byte code, byte authority, byte answer) {
        this.type = type;
        this.code = code;
        this.authority = authority;
        this.answer = answer;
    }
    public void sendSignUpInfo(DataOutputStream dos) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dataWrite = new DataOutputStream(bao);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String id, pw, address, name, phone;
            int age, state;
            System.out.println("id");
            id = br.readLine();
            System.out.println("pw");
            pw = br.readLine();
            System.out.println("address");
            address = br.readLine();
            System.out.println("name");
            name = br.readLine();
            System.out.println("phone");
            phone = br.readLine();
            System.out.println("age");
            age = Integer.parseInt(br.readLine());
            state = 0;
            System.out.println("[1] 고객 [2] 점주");
            int author = Integer.parseInt(br.readLine());
            if(author == 1) {
                authority = Protocol.CLIENT;
            }
            if(author == 2) {
                authority = Protocol.OWNER;
            }

            UserDTO user = new UserDTO(id, pw, address, name, phone, age, state, authority);
            byte[] bodyBytes = user.getBytes();
            size = bodyBytes.length;
            byte[] headerBytes = Protocol.getHeader(type, code, authority, answer, size);

            dataWrite.write(headerBytes);
            dataWrite.write(bodyBytes);

            dos.write(bao.toByteArray());
            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
