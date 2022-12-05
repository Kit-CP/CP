package Database.persistence.dao;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.StoreDTO;

import java.util.List;

public class StoreDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public StoreDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<StoreDTO> showAcceptedStore() { //승인된 가게 조회
        List<StoreDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.StoreMapper.showAcceptedStore");
        } finally {
            session.close();
        }
        return dtos;
    }

    public List<StoreDTO> showPendingStore() { //보류중인 가게 조회
        List<StoreDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.StoreMapper.showPendingStore");
        } finally {
            session.close();
        }
        return dtos;
    }

    public synchronized boolean insertStore(StoreDTO sdto) { //가게 등록
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try{
            session.insert("mapper.StoreMapper.insertStore", sdto);
            session.commit();
            result = true;
        }
        catch (Exception e) {
            session.rollback();
            result = false;
        }
        finally {
            session.close();
        }

        return result;
    }


    public synchronized boolean acceptStore(String name) { //가게 승인
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.StoreMapper.acceptStore", name);
            session.commit();
            result = true;
        }
        catch (Exception e) {
            session.rollback();
            result = false;
        }
        finally {
            session.close();
        }

        return result;
    }

    public String getStoreName(String user_id) {
        String store_name = "";
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            store_name = session.selectOne("mapper.StoreMapper.getStoreName", user_id);
        }
        finally {
            session.close();
        }

        return store_name;
    }

}
