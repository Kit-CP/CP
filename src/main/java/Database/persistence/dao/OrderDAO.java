package Database.persistence.dao;

import Database.persistence.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean makeOrder(OrderDTO dto) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.OrderMapper.makeOrder", dto);
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

    public synchronized boolean updateState(int order_id, int state) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order_id);
        map.put("state", state);
        try {
            sqlSession.update("mapper.OrderMapper.acceptOrder", map);
            sqlSession.commit();
            result = true;
        } catch (Exception e) {
            sqlSession.rollback();
            result = false;
        } finally {
            sqlSession.close();
        }

        return result;
    }

    public synchronized boolean cancelOrder(int order_id) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            sqlSession.update("mapper.OrderMapper.cancelOrder", order_id);
            result = true;
        } catch (Exception e) {
            sqlSession.rollback();
            result = false;
        } finally {
            sqlSession.close();
        }

        return result;
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

    public synchronized boolean updatePriceSum(int order_id, int newPriceSum) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Integer> param = new HashMap<>();
        param.put("order_id", order_id);
        param.put("priceSum", newPriceSum);
        try {
            sqlSession.update("mapper.OrderMapper.updatePriceSum", param);
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

    public List<OrderViewDTO> getOrderList(String store_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<OrderViewDTO> result;
        try {
            result = sqlSession.selectList("mapper.OrderMapper.getOrderList", store_name);
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public List<OrderViewDTO> getOrderFinishList(String store_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<OrderViewDTO> result;
        try {
            result = sqlSession.selectList("mapper.OrderMapper.getOrderFinishList", store_name);
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public List<MenuSalesDTO> getMenuSales(String store_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<MenuSalesDTO> result;
        try {
            result = sqlSession.selectList("mapper.OrderMapper.getMenuSales", store_name);
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public List<StoreSalesDTO> getStoreSales() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<StoreSalesDTO> result;
        try {
            result = sqlSession.selectList("mapper.OrderMapper.getStoreSales");
        }
        finally {
            sqlSession.close();
        }
        return result;
    }
}
