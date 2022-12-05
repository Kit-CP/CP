package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.UserDTO;

import java.util.ArrayList;
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
        SqlSession session = sqlSessionFactory.openSession(false);
        try{
            dtos = session.selectList("mapper.UserMapper.showAll");
            session.commit();
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

    public synchronized boolean updateInfor(String prev_User_ID, UserDTO dto) {
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        Map<String, Object> map = new HashMap<>();
        map.put("user_ID", prev_User_ID);
        map.put("new_User_ID", dto.getUser_ID());
        map.put("new_User_PW", dto.getUser_PW());
        map.put("new_user_address", dto.getUser_address());
        map.put("new_user_name", dto.getUser_name());
        map.put("new_user_phone", dto.getUser_phone());
        map.put("new_age", dto.getAge());

        try {
            session.update("mapper.UserMapper.updateInfor", map);
            session.commit();
            result = true;
        }
        catch (Exception e) {
            session.rollback();
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
        }
        finally {
            session.close();
        }

        return result;
    }

    public UserDTO login(UserDTO dto) {
        UserDTO userDTO = new UserDTO();
        SqlSession session = sqlSessionFactory.openSession();
        userDTO = session.selectOne("mapper.UserMapper.signIn", dto);
        session.close();
        return userDTO;
    }

    public List<UserDTO> getPendingStoreKeepers() {
        List<UserDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession();
        list = session.selectList("mapper.UserMapper.getPendingStoreKeepers");
        session.close();
        return list;
    }
}
