package Database.view;

import Database.persistence.dto.MenuDTO;
import Database.persistence.dto.StoreDTO;
import Util.State;

import java.util.List;

public class StoreView {
    public static void printAcceptedStore(List<StoreDTO> dtos) {
        for (StoreDTO dto : dtos) {
            StringBuilder sb = new StringBuilder();
            sb.append(dto.getStore_name());
            sb.append(", ");
            sb.append(dto.getInformation());
            sb.append(", ");
            sb.append(dto.getStore_address());
            sb.append(", ");
            sb.append(dto.getStore_phone());
            sb.append(", ");
            sb.append(dto.getStore_score());
            System.out.println(sb);
        }
        System.out.println();
    }

    public static void printMyStores(List<StoreDTO> dtos) {
        for (StoreDTO dto : dtos) {
            StringBuilder sb = new StringBuilder();
            sb.append(dto.getStore_name());
            sb.append(", ");
            sb.append(dto.getInformation());
            sb.append(", ");
            sb.append(dto.getStore_address());
            sb.append(", ");
            sb.append(dto.getStore_phone());
            sb.append(", ");
            sb.append(dto.getStore_score());
            sb.append(", ");
            sb.append(State.getState(dto.getIsAccept()));
            System.out.println(sb);
        }
        System.out.println();
    }

}
