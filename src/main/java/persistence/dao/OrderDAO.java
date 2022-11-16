package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void makeOrder(List<OrderDTO> dtos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for (OrderDTO dto : dtos) {
                sqlSession.insert("mapper.OrderMapper.makeOrder", dto);
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
