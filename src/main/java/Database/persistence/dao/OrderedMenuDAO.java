package Database.persistence.dao;

import Database.persistence.dto.OrderedMenuDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderedMenuDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderedMenuDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean orderMenu(OrderedMenuDTO dto) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.OrderedMenuMapper.orderMenu", dto);
            sqlSession.commit();
            result = true;
        }
        catch (Exception e) {
            sqlSession.rollback();
            result = false;
        }
        finally {
            sqlSession.close();
        }

        return result;
    }

    public synchronized boolean updatePrice(int orderedMenuId, int newPrice) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Integer> param = new HashMap<>();
        param.put("ordered_menu_id", orderedMenuId);
        param.put("price", newPrice);
        try {
            sqlSession.update("mapper.OrderedMenuMapper.updatePrice", param);
            sqlSession.commit();
            result = true;
        }
        catch (Exception e) {
            sqlSession.rollback();
            result = false;
        }
        finally {
            sqlSession.close();
        }

        return result;
    }
}
