package Database.persistence.dao;

import Database.persistence.dto.OrderedOptionDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderedOptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public OrderedOptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean orderedOption(OrderedOptionDTO dto) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.OrderedOptionMapper.orderOption", dto);
            sqlSession.commit();
            result = true;
        }
        catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            result = false;
        }
        finally {
            sqlSession.close();
        }

        return result;
    }
}
