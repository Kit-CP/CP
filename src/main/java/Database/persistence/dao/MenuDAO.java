package Database.persistence.dao;

import Database.persistence.dto.MenuOptionDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.MenuDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MenuDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean insertMenuAll(List<MenuDTO> dtos) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( MenuDTO dto : dtos ) {
                sqlSession.insert("mapper.MenuMapper.insertMenuAll", dto);
                sqlSession.commit();
                result = true;
            }
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

    public List<MenuOptionDTO> showMenu() {
        List<MenuOptionDTO> result;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            result = sqlSession.selectList("mapper.MenuMapper.showMenu");
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public synchronized boolean updateMenu(String name, String newName, Integer price) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_menu_name", newName);
        param.put("new_price", price);
        try {
            sqlSession.update("mapper.MenuMapper.updateMenu", param);
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

    public synchronized boolean updateMenu(String name, String newName) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, String> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_menu_name", newName);
        try {
            sqlSession.update("mapper.MenuMapper.updateMenu", param);
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

    public synchronized boolean updateMenu(String name, Integer price) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_price", price);
        try {
            sqlSession.update("mapper.MenuMapper.updateMenu", param);
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

    public int getStock(String menuName) {
        int temp = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            temp = sqlSession.selectOne("mapper.MenuMapper.getStock", menuName);
        }
        finally {
            sqlSession.close();
        }
        return temp;
    }

    public synchronized boolean updateStock(String menuName, int newStock) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> param = new HashMap<>();
        param.put("menu_name", menuName);
        param.put("new_stock", newStock);
        try {
            sqlSession.update("mapper.MenuMapper.updateStock", param);
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

    public int getMenuPrice(String menu_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int result;
        try {
            result = sqlSession.selectOne("mapper.MenuMapper.getMenuPrice", menu_name);
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public synchronized boolean judgeMenu(Map<String, Object> param) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update("mapper.MenuMapper.judgeMenu", param);
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
}
