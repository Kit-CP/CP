package Database.view;

import java.util.List;
import java.util.Map;

public class ReviewView {
    public static void printAll(List<Map<String, Object>> list, int crtPage , int numOfPages) {
        System.out.println("리뷰번호\t아이디\t\t가게이름\t\t별점\t\t주문번호\t\t내용");
        for (Map<String, Object> map : list) {
            StringBuilder sb = new StringBuilder();
            sb.append(map.get("review_id"));
            sb.append("\t\t");
            sb.append(map.get("user_id"));
            sb.append("\t\t");
            sb.append(map.get("store_name"));
            sb.append("\t\t");
            sb.append(map.get("review_score"));
            sb.append("\t\t");
            sb.append(map.get("order_id"));
            sb.append("\t\t\t");
            sb.append(map.get("content"));
            System.out.println(sb);
        }
        System.out.printf("현재 페이지 번호 : %d\n", crtPage);
        System.out.printf("마지막 페이지 번호 : %d\n", numOfPages/2 + numOfPages%2);
    }
}
