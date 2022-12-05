package Database.persistence.dao;

import Database.persistence.dto.StoreDTO;
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

    public synchronized boolean insertOptionAll(List<OptionDTO> dtos) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            for ( OptionDTO dto : dtos ) {
                sqlSession.insert("mapper.OptionMapper.insertOption", dto);
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

    public synchronized boolean judgeOption(Map<String, Object> param) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update("mapper.OptionMapper.judgeOption", param);
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

    public List<OptionDTO> showOptions() {
        List<OptionDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.OptionMapper.showOptions");
        } finally {
            session.close();
        }
        return dtos;
    }

    public List<StoreDTO> showPendingOption() {
        List<StoreDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.StoreMapper.showPendingMenu");
        } finally {
            session.close();
        }
        return dtos;
    }
}
