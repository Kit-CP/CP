package Database.view;

import Database.persistence.dto.StoreDTO;

import java.util.List;

public class StoreView {
    public void printAll(List<StoreDTO> dtos) {
        System.out.println("매장 정보");
        for (StoreDTO dto : dtos) {
            StringBuilder sb = new StringBuilder();
            sb.append(dto.getStore_name());
            sb.append(", ");
            sb.append(dto.getInformation());
            sb.append(", ");
            sb.append(dto.getStore_address());
            sb.append(", ");
            sb.append(dto.getStore_phone());
            System.out.println(sb);
        }
    }
}
