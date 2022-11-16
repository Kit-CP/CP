package Database.view;

import Database.persistence.dto.StoreDTO;

import java.util.List;

public class StoreView {
    public void printAll(List<StoreDTO> dtos) {
        System.out.println("매장 정보");
        for (StoreDTO dto : dtos) {
            System.out.println(dto.toString());
        }
    }
}
