package Network;

import Database.persistence.dto.IDTO;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

public class MyListSerializer<E extends IDTO> {

    public MyListSerializer() {
    }

    public byte[] listToByte(List<E> dtos) {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        if ( dtos.size() == 0 ) {
            return null;
        }
        try {
            byte[] bytes = null;
            int dtoCount = 0;
            if (dtos != null) {
                dtoCount = dtos.size();
                for (E dto : dtos) {
                    bytes = ArrayUtils.addAll(bytes, dto.getBytes());
                }
            }
            dos.writeInt(dtoCount);
            dos.write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();

    }

}
