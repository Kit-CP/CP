package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.MenuHasOptionDTO;

import java.util.List;

public class MenuHasOptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MenuHasOptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean insertMenuOption(List<MenuHasOptionDTO> dtos) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( MenuHasOptionDTO dto : dtos ) {
                sqlSession.insert("mapper.MenuHasOptionMapper.insertMenuOption", dto);
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
}
