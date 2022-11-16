package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.OrderedOptionDTO;

import java.util.List;

public class OrderedOptionDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderedOptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void orderedOption(OrderedOptionDTO dto) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.OrderedOptionMapper.orderedOption", dto);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        }
        finally {
            sqlSession.close();
        }
    }
}
