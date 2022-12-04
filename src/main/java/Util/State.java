package Util;

public class State {
    private final static String[] deliveryState = {"접수대기", "취소", "배달중", "배달완료"};
    private final static String[] authority = {"익명", "고객", "점주", "관리자"};
    private final static String[] state = {"보류", "승인", "거절"};

    public static String getDeliverySate(int i) {
        return deliveryState[i];
    }

    public static String getAuthority(int i) {
        return authority[i];
    }

    public static String getState(int i) {
        return state[i];
    }
}