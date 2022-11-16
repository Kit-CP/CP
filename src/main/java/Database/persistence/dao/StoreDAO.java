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

    public void insertStore(StoreDTO sdto) {
        SqlSession session = sqlSessionFactory.openSession(false);
        try{
            session.insert("mapper.StoreMapper.insertStore", sdto);
            session.commit();
        }
        catch (Exception e) {
            session.rollback();
        }
        finally {
            session.close();
        }
    }

}
