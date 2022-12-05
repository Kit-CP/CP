package Network;

import Database.persistence.dto.IDTO;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class MyListSerializer<E extends IDTO> {

    public MyListSerializer() {
    }
    public byte[] listToByte(List<E> dtos) {
        byte[] bytes = null;
        for ( E dto : dtos ) {
            bytes = ArrayUtils.addAll(bytes, dto.getBytes());
        }
        return bytes;
    }

}
