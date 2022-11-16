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

    public void insertMenuAll(List<MenuDTO> dtos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( MenuDTO dto : dtos ) {
                sqlSession.insert("mapper.MenuMapper.insertMenuAll", dto);
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

    public void updateMenu(String name, String newName, Integer price) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_menu_name", newName);
        param.put("new_price", price);
        try {
            sqlSession.update("mapper.MenuMapper.updateMenuAll", param);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    public void updateMenu(String name, String newName) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, String> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_menu_name", newName);
        try {
            sqlSession.update("mapper.MenuMapper.updateMenuName", param);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    public void updateMenu(String name, Integer price) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_price", price);
        try {
            sqlSession.update("mapper.MenuMapper.updateMenuPrice", param);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
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

    public void updateStock(String menuName, int newStock) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> param = new HashMap<>();
        param.put("menu_name", menuName);
        param.put("new_stock", newStock);
        try {
            sqlSession.update("mapper.MenuMapper.updateStock", param);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }
}
