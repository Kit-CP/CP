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

    public synchronized boolean makeOrder(NewOrderDTO dto) {
        OrderedMenuDTO orderedMenuDTO = new OrderedMenuDTO();
        OrderedOptionDTO orderedOptionDTO = new OrderedOptionDTO();
        Map<String, Object> map = new HashMap<>();
        int priceSum = 0;
        int stock = 0;
        int menuPrice = 0;
        int optionPrice = 0;
        int priceSumOptions = 0;
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        try {
            sqlSession.insert("mapper.OrderMapper.makeOrder", dto); // 주문 생성
            String[] menu_options = dto.getMenus_options().split("\\$"); // 각 메뉴 구분
            for (int i = 0; i < menu_options.length; i++) {

                String[] list = menu_options[i].split("/");     // 메뉴와 옵션들을 배열로
                String menu_name = list[0];
                orderedMenuDTO.setOrder_id(dto.getOrder_id());
                orderedMenuDTO.setMenu_name(menu_name);
                stock = sqlSession.selectOne("mapper.MenuMapper.getStock", menu_name);      // 현재 재고 가져옴


                if (stock > 0) {        // 재고가 있으면
                    sqlSession.insert("mapper.OrderedMenuMapper.orderMenu", orderedMenuDTO); //주문 메뉴 생성
                    map.put("menu_name", menu_name);
                    map.put("new_stock", stock - 1);
                    sqlSession.update("mapper.MenuMapper.updateStock", map);    // 메뉴재고 1 차감
                    menuPrice = sqlSession.selectOne("mapper.MenuMapper.getMenuPrice", menu_name);  // 메뉴가격 가져옴
                    priceSum += menuPrice;
                    orderedOptionDTO.setOrdered_menu_id(orderedMenuDTO.getOrdered_menu_id());

                    priceSumOptions = 0;
                    for (int j = 1; j < list.length; j++) {
                        orderedOptionDTO.setOption_name(list[j]);
                        sqlSession.insert("mapper.OrderedOptionMapper.orderOption", orderedOptionDTO); // 주문 옵션 생성
                        optionPrice = sqlSession.selectOne("mapper.OptionMapper.getOptionPrice", orderedOptionDTO.getOption_name());    // 옵션 가격 가져옴
                        priceSum += optionPrice;
                        priceSumOptions += optionPrice;
                    }
                    map = new HashMap<>();
                    map.put("ordered_menu_id", orderedMenuDTO.getOrdered_menu_id());
                    map.put("price", menuPrice + priceSumOptions);
                    sqlSession.update("mapper.OrderedMenuMapper.updatePrice", map);     // 주문한 메뉴의 가격 업데이트
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

    /*public synchronized boolean makeOrder(OrderDTO dto) {                 // 사용안함
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
    }*/

    public synchronized boolean updateState(OrderDTO orderDTO) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderDTO.getOrder_id());
        map.put("store_name", orderDTO.getStore_name());
        map.put("state", orderDTO.getState());
        try {
            sqlSession.update("mapper.OrderMapper.judgeOrder", map);
            sqlSession.commit();
            result = true;
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        return result;
    }

    public synchronized boolean cancelOrder(OrderDTO orderDTO) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try {
            int canceledOrder = sqlSession.update("mapper.OrderMapper.cancelOrder", orderDTO);

            if (canceledOrder == 1) {
                sqlSession.update("mapper.OrderMapper.restockMenu", orderDTO);
                result = true;
            }
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        return result;
    }

    public OrderDTO getOrderState(OrderDTO orderDTO) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        OrderDTO dto = new OrderDTO();
        try {
            dto.setState(sqlSession.selectOne("mapper.OrderMapper.getOrderState", orderDTO.getOrder_id()));
        } catch (Exception e) {
            sqlSession.rollback();
            dto.setState(-1);
        } finally {
            sqlSession.close();
        }
        return dto;
    }

    /*public synchronized boolean updatePriceSum(int order_id, int newPriceSum) {       // 사용안함
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
    }*/

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

    public List<MenuSalesDTO> getMenuSales(String store_name) {         // 메뉴이름, 시킨횟수, 총 가격
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

    public List<StoreSalesDTO> getStoreSales() {            // 가게이름, 주문횟수, 총 매출
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
