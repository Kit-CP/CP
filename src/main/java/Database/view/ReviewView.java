package Database.view;

import Database.persistence.dto.ReviewDTO;

import java.util.List;

public class ReviewView {
    public static void printAll(List<ReviewDTO> list, int crtPage , int numOfPages) {
        System.out.println("리뷰번호\t아이디\t\t별점\t\t\t\t\t주문번호\t\t주문내용\t\t답글");
        for (ReviewDTO dto : list) {
            StringBuilder sb = new StringBuilder();
            sb.append(dto.getReview_id());
            sb.append("\t\t");
            sb.append(dto.getUser_id());
            sb.append("\t\t");
            sb.append(dto.getReview_score());
            sb.append("\t\t");
            sb.append(dto.getOrder_id());
            sb.append("\t\t\t");
            sb.append(dto.getContent());
            sb.append("\t\t\t");
            sb.append(dto.getReply());
            System.out.println(sb);
        }
        System.out.printf("현재 페이지 번호 : %d\n", crtPage);
        System.out.printf("마지막 페이지 번호 : %d\n", numOfPages/2 + numOfPages%2);
    }
}
