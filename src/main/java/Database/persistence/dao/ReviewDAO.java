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

    public void writeReview(ReviewDTO dto) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            sqlSession.insert("mapper.ReviewMapper.writeReview", dto);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public List<Map<String, Object>> showReview(String user_id, int crtPage) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<Map<String, Object>> list = null;
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("numOfPages", crtPage*2);
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
}