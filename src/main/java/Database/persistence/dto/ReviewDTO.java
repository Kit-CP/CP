package Database.persistence.dto;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ReviewDTO implements IDTO {
    private int review_id;
    private String user_id;
    private String content;
    private int review_score;
    private int order_id;
    private String reply;

    public ReviewDTO(String content, int review_score, int order_id) {
        review_id = 0;
        user_id = "";
        this.content = content;
        this.review_score = review_score;
        this.order_id = order_id;
        this.reply = "";
    }

    public ReviewDTO() {
        this.review_id = 0;
        this.user_id = "";
        this.content = "";
        this.review_score = 0;
        this.order_id = 0;
        this.reply = "";
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeInt(review_id);
            dos.writeUTF(user_id);
            dos.writeUTF(content);
            dos.writeInt(review_score);
            dos.writeInt(order_id);
            dos.writeUTF(reply);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static ReviewDTO readReviewDTO(DataInputStream dis) {
        ReviewDTO dto = new ReviewDTO();
        try {
            dto.setReview_id(dis.readInt());
            dto.setUser_id(dis.readUTF());
            dto.setContent(dis.readUTF());
            dto.setReview_score(dis.readInt());
            dto.setOrder_id(dis.readInt());
            dto.setReply(dis.readUTF());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
