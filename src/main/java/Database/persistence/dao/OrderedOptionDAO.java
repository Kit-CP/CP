package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.OrderedOptionDTO;

import java.util.List;

public class OrderedOptionDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderedOptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void orderOption(List<OrderedOptionDTO> dtos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( OrderedOptionDTO dto : dtos ) {
                sqlSession.insert("mapper.OrderedOptionMapper.orderOption", dto);
                sqlSession.commit();
            }
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }
}
