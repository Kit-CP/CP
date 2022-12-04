package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.StoreDTO;

import java.util.List;

public class StoreDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public StoreDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<StoreDTO> showAcceptedStore(){
        List<StoreDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.StoreMapper.showAcceptedStore");
        } finally {
            session.close();
        }
        return dtos;
    }

    public synchronized boolean insertStore(StoreDTO sdto) {
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

    public synchronized boolean acceptStore(String name) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
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

}
