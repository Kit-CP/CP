package Database.view;

import Database.persistence.dto.ReviewDTO;

import java.util.List;

public class ReviewView {
    public static void printAll(List<ReviewDTO> list, int crtPage , int numOfPages) {
        for (ReviewDTO dto : list) {
            System.out.printf("리뷰번호 : %d  |  아이디 : %s  |  별점 : %d  |  주문번호 : %d  |  리뷰내용 : %s  |  답글 : %s\n",
                    dto.getReview_id(), dto.getUser_id(), dto.getReview_score(), dto.getOrder_id(), dto.getContent(), dto.getReply());
        }
        System.out.printf("현재 페이지 번호 : %d\n", crtPage);
        System.out.printf("마지막 페이지 번호 : %d\n", numOfPages/2 + numOfPages%2);
    }

    public static void print(List<ReviewDTO> list) {
        for (ReviewDTO dto : list) {
            System.out.printf("리뷰번호 : %d  |  아이디 : %s  |  별점 : %d  |  주문번호 : %d  |  리뷰내용 : %s  |  답글 : %s\n",
                    dto.getReview_id(), dto.getUser_id(), dto.getReview_score(), dto.getOrder_id(), dto.getContent(), dto.getReply());
        }
    }
}
