package Database.persistence.dao;


import Database.persistence.dto.ReviewDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public ReviewDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized boolean writeReview(ReviewDTO dto) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.ReviewMapper.writeReview", dto);
            sqlSession.commit();
            result = true;
        } catch (Exception e) {
            sqlSession.rollback();
            result = false;
        } finally {
            sqlSession.close();
        }

        return result;
    }

    public List<ReviewDTO> showReview(String user_id, String store_name, int crtPage) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<ReviewDTO> list = null;
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("numOfPages", crtPage*2);
        map.put("store_name", store_name);
        try {
            list = sqlSession.selectList("mapper.ReviewMapper.showReview", map);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return list;
    }

    public int getReviewNum(String user_id) {
        int numOfReviews = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            numOfReviews = sqlSession.selectOne("mapper.ReviewMapper.getReviewNum", user_id);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return numOfReviews;
    }

    public synchronized boolean writeReply(String reply, int review_id) {
        boolean result = false;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> map = new HashMap<>();
        map.put("reply", reply);
        map.put("review_id", review_id);
        try{
            sqlSession.update("mapper.ReviewMapper.writeReply", map);
            sqlSession.commit();
            result = true;
        } catch (Exception e) {
            sqlSession.rollback();
            result = false;
        } finally {
            sqlSession.close();
        }

        return result;
    }
}
