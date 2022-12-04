package Database.persistence.dao;

import Database.persistence.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public OrderDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean newMakeOrder(NewOrderDTO dto) {
        OrderedMenuDTO orderedMenuDTO = new OrderedMenuDTO();
        OrderedOptionDTO orderedOptionDTO = new OrderedOptionDTO();
        Map<String, Object> map = new HashMap<>();
        int priceSum = 0;
        int stock = 0;
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        try {
            sqlSession.insert("mapper.OrderMapper.makeOrder", dto); // 주문 생성

            for (String menu_opitons : dto.getMenus_options()) {

                String[] list = menu_opitons.split("/");
                String menu_name = list[0];
                orderedMenuDTO.setOrder_id(dto.getOrder_id());
                orderedMenuDTO.setMenu_name(menu_name);
                stock = sqlSession.selectOne("mapper.MenuMapper.getStock", menu_name);      // 현재 재고 가져옴


                if (stock > 0) {        // 재고가 있으면
                    sqlSession.insert("mapper.OrderedMenuMapper.orderMenu", orderedMenuDTO); //주문 메뉴 생성
                    map.put("menu_name", menu_name);
                    map.put("new_stock", stock - 1);
                    sqlSession.update("mapper.MenuMapper.updateStock", map);    // 메뉴재고 1 차감
                    int price = sqlSession.selectOne("mapper.MenuMapper.getMenuPrice", menu_name);  // 메뉴가격 가져옴
                    priceSum += price;
                    orderedOptionDTO.setOrdered_menu_id(orderedMenuDTO.getOrdered_menu_id());

                    for (int i = 1; i < list.length; i++) {
                        orderedOptionDTO.setOption_name(list[i]);
                        sqlSession.insert("mapper.OrderedOptionMapper.orderOption", orderedOptionDTO); // 주문 옵션 생성
                        price = sqlSession.selectOne("mapper.OptionMapper.getOptionPrice", list[i]);    // 옵션 가격 가져옴
                        priceSum += price;
                    }
                }
                else {
                    throw new Exception("재고가 부족하여 오류 발생");
                }
            }
            map = new HashMap<>();
            map.put("order_id", dto.getOrder_id());
            map.put("priceSum", priceSum);
            sqlSession.update("mapper.OrderMapper.updatePriceSum", map);    // 주문 테이블 가격 업데이트
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

    public synchronized boolean updateState(int order_id, String store_name, int state) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order_id);
        map.put("store_name", store_name);
        map.put("state", state);
        try {
            int success = sqlSession.update("mapper.OrderMapper.judgeOrder", map);
            sqlSession.commit();
            if (success == 1) {
                result = true;
            } else {
                result = false;
            }
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
            int canceledOrder = sqlSession.update("mapper.OrderMapper.cancelOrder", order_id);

            if (canceledOrder == 1) {
                sqlSession.update("mapper.OrderMapper.restockMenu", order_id);
                result = true;
            }
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
