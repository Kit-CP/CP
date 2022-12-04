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

    public synchronized void signUpStoreKeeper(UserDTO dto) {
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.insert("mapper.UserMapper.signUpStoreKeeper", dto);
            session.commit();;
        }
        catch (Exception e) {
            session.rollback();
        }
        finally {
            session.close();
        }
    }

    public synchronized void signUpClient(UserDTO dto) {
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.insert("mapper.UserMapper.signUpClient", dto);
            session.commit();;
        }
        catch (Exception e) {
            session.rollback();
        }
        finally {
            session.close();
        }
    }

    public synchronized void updateInfor(Map<String, Object> param) {
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.update("mapper.UserMapper.updateInfor", param);
            session.commit();
        }
        catch (Exception e) {
            session.rollback();
        }
        finally {
            session.close();
        }
    }

    public synchronized void judgeStoreKeeper(Map<String, Object> param) {
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            session.update("mapper.UserMapper.judgeStoreKeeper", param);
            session.commit();
        }
        catch (Exception e) {
            session.rollback();
        }
        finally {
            session.close();
        }
    }
}
