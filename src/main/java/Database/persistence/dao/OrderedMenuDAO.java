package Database.persistence.dao;

import Database.persistence.dto.OrderedMenuDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class OrderedMenuDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderedMenuDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void orderMenu(List<OrderedMenuDTO> dtos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( OrderedMenuDTO dto : dtos ) {
                sqlSession.insert("mapper.OrderedMenuMapper.orderMenu", dto);
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
