package Database.persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Database.persistence.dto.StoreDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public StoreDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<StoreDTO> showAcceptedStore() { //승인된 가게 조회
        List<StoreDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.StoreMapper.showAcceptedStore");
        } finally {
            session.close();
        }
        return dtos;
    }

    public List<StoreDTO> showPendingStore() { //보류중인 가게 조회
        List<StoreDTO> dtos = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            dtos = session.selectList("mapper.StoreMapper.showPendingStore");
        } finally {
            session.close();
        }
        return dtos;
    }

    public synchronized boolean insertStore(StoreDTO sdto) { //가게 등록
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession(false);
        try{
            session.insert("mapper.StoreMapper.insertStore", sdto);
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


    public synchronized boolean judgeStore(Map<String, Object> param) { //가게 승인
        boolean result = false;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.StoreMapper.judgeStore", param);
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

    public String getStoreName(String user_id) {
        String store_name = "";
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            store_name = session.selectOne("mapper.StoreMapper.getStoreName", user_id);
        }
        finally {
            session.close();
        }

        return store_name;
    }

    public List<StoreDTO> getMyStoreList(String user_id) {
        List<StoreDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("mapper.StoreMapper.getMyStoreList", user_id);
        }
        finally {
            session.close();
        }

        return list;
    }

    public void updateScore(int newScore, int orderId) {
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            String store_name = session.selectOne("mapper.OrderMapper.getStoreName", orderId);
            int reviewNum = session.selectOne("mapper.ReviewMapper.getStoreReviewNum", store_name);
            int score = session.selectOne("mapper.StoreMapper.getScore", store_name);
            int avgScore = Math.round((score + newScore) / reviewNum);
            Map<String, Object> param = new HashMap<>();
            param.put("store_name", store_name);
            param.put("store_score", avgScore);
            session.update("mapper.StoreMapper.updateScore", param);
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
