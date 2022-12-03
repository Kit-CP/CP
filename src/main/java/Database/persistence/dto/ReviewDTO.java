package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
    private int review_id;
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
}
