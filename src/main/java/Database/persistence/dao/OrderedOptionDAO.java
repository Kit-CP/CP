package Database.persistence.dao;

import Database.persistence.dto.OrderedOptionDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderedOptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public OrderedOptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void orderOption(OrderedOptionDTO dto) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.OrderedOptionMapper.orderOption", dto);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }
}
