package Database.view;

import Database.persistence.dto.MenuDTO;
import Database.persistence.dto.StoreDTO;
import Util.State;

import java.util.List;

public class StoreView {
    public static void printAcceptedStore(List<StoreDTO> dtos) {
        for (StoreDTO dto : dtos) {
            System.out.printf("가게이름 : %s |  가게정보 : %s |  주소 : %s |  전화번호 : %s |  평점 : %d\n", dto.getStore_name(), dto.getInformation(), dto.getStore_address(), dto.getStore_phone(), dto.getStore_score());
        }
    }

    public static void printMyStores(List<StoreDTO> dtos) {
        for (StoreDTO dto : dtos) {
            System.out.printf("가게이름 : %s |  가게정보 : %s |  주소 : %s |  전화번호 : %s |  평점 : %d |  상태 : %s\n", dto.getStore_name(), dto.getInformation(), dto.getStore_address(), dto.getStore_phone(), dto.getStore_score(), State.getState(dto.getIsAccept()));
        }
    }

}
