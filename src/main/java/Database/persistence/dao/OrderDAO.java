package Database.persistence.dao;

import Database.persistence.dto.OrderDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void makeOrder(OrderDTO dto) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.OrderMapper.makeOrder", dto);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    public List<Integer> getLastOrderNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Integer> temp = new ArrayList<>();
        try{
            temp = sqlSession.selectOne("mapper.OrderMapper.getLastOrderNum");
        }
        finally {
            sqlSession.close();
        }
        return temp;
    }
}
