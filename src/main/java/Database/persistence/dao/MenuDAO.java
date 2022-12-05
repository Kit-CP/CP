package Database.persistence.dao;

import Database.persistence.dto.MenuHasOptionDTO;
import Database.persistence.dto.MenuOptionDTO;
import Database.persistence.dto.StoreDTO;
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

    public synchronized boolean insertMenuAll(List<MenuDTO> menuDTOS, List<String> menu_options) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        int size = menuDTOS.size();
        try {
            for ( int i = 0; i < size; i++ ) {
                sqlSession.insert("mapper.MenuMapper.insertMenuAll", menuDTOS.get(i));
                String menu_name = menuDTOS.get(i).getMenu_name();
                String[] options = menu_options.get(i).split("/");
                for ( String option : options ) {
                    MenuHasOptionDTO menuHasOptionDTO = new MenuHasOptionDTO();
                    menuHasOptionDTO.setMenu_name(menu_name);
                    menuHasOptionDTO.setOption_name(option);
                    sqlSession.insert("mapper.MenuHasOptionMapper.insertMenuOption", menuHasOptionDTO);
                }
            }
            sqlSession.commit();
            result = true;
        }
        catch (Exception e) {
            sqlSession.rollback();
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

    public List<MenuDTO> showAcceptedMenu() {
        List<MenuDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.MenuMapper.showAcceptedMenu");
        } finally {
            session.close();
        }
        return dtos;
    }

    public List<MenuDTO> showPendingMenu() {
        List<MenuDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.MenuMapper.showPendingMenu");
        } finally {
            session.close();
        }
        return dtos;
    }
}
