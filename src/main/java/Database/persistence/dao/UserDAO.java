package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public UserDAO(SqlSessionFactory factory){
        this.sqlSessionFactory = factory;
    }
    public List<UserDTO> showAll(){
        List<UserDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.UserMapper.showAll");
        } finally {
            session.close();
        }
        return dtos;
    }

    public synchronized boolean signUpStoreKeeper(UserDTO dto) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.insert("mapper.UserMapper.signUpStoreKeeper", dto);
            session.commit();;
            result = true;
        }
        catch (Exception e) {
            session.rollback();
            result = false;
        }
        finally {
            session.close();
        }

        return result;
    }

    public synchronized boolean signUpClient(UserDTO dto) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.insert("mapper.UserMapper.signUpClient", dto);
            session.commit();
            result = true;
        }
        catch (Exception e) {
            session.rollback();
            result = false;
        }
        finally {
            session.close();
        }

        return result;
    }

    public synchronized boolean updateInfor(Map<String, Object> param) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.update("mapper.UserMapper.updateInfor", param);
            session.commit();
            result = true;
        }
        catch (Exception e) {
            session.rollback();
            result = false;
        }
        finally {
            session.close();
        }

        return result;
    }

    public synchronized boolean judgeStoreKeeper(Map<String, Object> param) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.update("mapper.UserMapper.judgeStoreKeeper", param);
            session.commit();
            result = true;
        }
        catch (Exception e) {
            session.rollback();
            result = false;
        }
        finally {
            session.close();
        }

        return result;
    }

    public boolean signIn(UserDTO dto) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            result = session.selectOne("mapper.UserMapper.signIn", dto);
        } finally {
            session.close();
        }

        return result;
    }
}
