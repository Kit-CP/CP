package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.MyBatisConnectionFactory;
import persistence.dto.UserDTO;

import java.util.List;

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
}
