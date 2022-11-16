package Util;

public class State {
    private final static String[] state = {"접수대기", "취소", "배달중", "배달완료"};

    public static String get(int i) {
        return state[i];
    }
}