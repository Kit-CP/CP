package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.OptionDTO;

import java.util.List;
import java.util.Map;

public class OptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public OptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized void insertOptionAll(List<OptionDTO> dtos) {
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

    public int getOptionPrice(String option_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int result;
        try {
            result = sqlSession.selectOne("mapper.OptionMapper.getOptionPrice", option_name);
        }
        finally {
            sqlSession.close();
        }
        return result;
    }

    public synchronized void judgeOption(Map<String, Object> param) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update("mapper.OptionMapper.judgeOption", param);
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
