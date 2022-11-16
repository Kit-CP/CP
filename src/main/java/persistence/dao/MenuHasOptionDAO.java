package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.OrderedOption;

import java.util.List;

public class MenuHasOptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MenuHasOptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertMenuOption(List<OrderedOption> dtos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( OrderedOption dto : dtos ) {
                sqlSession.insert("mapper.MenuHasOptionMapper.insertMenuOption", dto);
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
}
