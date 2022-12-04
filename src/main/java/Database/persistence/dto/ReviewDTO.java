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
@NoArgsConstructor
public class ReviewDTO implements IDTO {
    private int review_id; //auto
    private String content;
    private int review_score;
    private int order_id;
    private String reply;

    public ReviewDTO(String content, int review_score, int order_id) {
        this.content = content;
        this.review_score = review_score;
        this.order_id = order_id;
        this.reply = "";
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(review_id);
        dos.writeUTF(content);
        dos.writeInt(review_score);
        dos.writeInt(order_id);
        dos.writeUTF(reply);

        return buf.toByteArray();
    }

    public static ReviewDTO readReviewDTO(DataInputStream dis) throws IOException {
        ReviewDTO dto = new ReviewDTO();
        dto.setReview_id(dis.readInt());
        dto.setContent(dis.readUTF());
        dto.setReview_score(dis.readInt());
        dto.setOrder_id(dis.readInt());
        dto.setReply(dis.readUTF());

        return dto;
    }
}
