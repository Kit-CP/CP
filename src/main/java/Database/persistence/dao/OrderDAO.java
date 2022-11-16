package Database.persistence.dao;

import Database.persistence.dto.OrderDTO;
import Database.persistence.dto.OrderedMenuDTO;
import Database.persistence.dto.OrderedOptionDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

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

    public void updateState(int order_id, int state) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order_id);
        map.put("state", state);
        try {
            sqlSession.update("mapper.OrderMapper.acceptOrder", map);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public void cancelOrder(int order_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            sqlSession.update("mapper.OrderMapper.cancelOrder", order_id);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public int getOrderState(int order_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        int temp = -1;
        try {
            temp = sqlSession.selectOne("mapper.OrderMapper.getOrderState", order_id);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return temp;
    }
}
