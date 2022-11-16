package Database.persistence.dto;

public class OrderDTO {
    private int order_id;
    private String User_ID;
    private String Store_name;
    private int priceSum;
    private int state;

    public OrderDTO(String user_ID, String store_name) {
        User_ID = user_ID;
        Store_name = store_name;
    }
}
