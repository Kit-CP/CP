package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.MenuDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public List<MenuDTO> showMenu() {
        List<MenuDTO> result;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            result = sqlSession.selectList("mapper.MenuMapper.showMenu");
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public void updateMenu(String name, String newname, int price) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, String> param = new HashMap<>();
        param.put("menu_name", name);
        param.put("new_menu_name", newname);
        param.put("new_price", String.valueOf(price));
        try {
            sqlSession.update("mapper.MenuMapper.updateMenu", param);
            sqlSession.commit();
        }
        catch (Exception e) {
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    public void updateMenu(String name) {

    }

    public void updateMenu(int price) {

    }
}
