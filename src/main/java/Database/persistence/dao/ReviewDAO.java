package Database.persistence.dao;


import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dto.ReviewDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
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
            OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            int state = orderDAO.getOrderState(dto.getOrder_id());
            if ( state == 3 ) {
                sqlSession.insert("mapper.ReviewMapper.writeReview", dto);
                sqlSession.commit();
                result = true;
            }
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public List<ReviewDTO> showUserReview(String user_id, String store_name, int crtPage) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<ReviewDTO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("numOfPages", crtPage*2);
        map.put("store_name", store_name);
        try {
            list = sqlSession.selectList("mapper.ReviewMapper.showUserReview", map);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return list;
    }

    public List<ReviewDTO> showStoreReview(String store_name, String user_id, int crtPage) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<ReviewDTO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("store_name", store_name);
        map.put("numOfPages", crtPage*2);
        map.put("user_id", user_id);
        try {
            list = sqlSession.selectList("mapper.ReviewMapper.showStoreReview", map);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        return list;
    }

    public List<ReviewDTO> showStoreUserReview(String store_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        List<ReviewDTO> list = new ArrayList<>();
        try {
            list = sqlSession.selectList("mapper.ReviewMapper.showStoreUserReview", store_name);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        return list;
    }

    public int getUserReviewNum(String user_id, String store_name) {
        int numOfReviews = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);

        try {
            numOfReviews = sqlSession.selectOne("mapper.ReviewMapper.getUserReviewNum", user_id);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return numOfReviews;
    }

    public int getStoreReviewNum(String store_name) {
        int numOfReviews = -1;
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            numOfReviews = sqlSession.selectOne("mapper.ReviewMapper.getStoreReviewNum", store_name);
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
            int updated = sqlSession.update("mapper.ReviewMapper.writeReply", map);
            sqlSession.commit();
            if (updated > 0) {
                result = true;
            }
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        return result;
    }
}
