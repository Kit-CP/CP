package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.OptionDTO;

import java.util.List;

public class OptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public OptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertOptionAll(List<OptionDTO> dtos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( OptionDTO dto : dtos ) {
                sqlSession.insert("mapper.OptionMapper.insertOption", dto);
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
