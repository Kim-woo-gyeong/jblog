package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class BlogRepository {

   @Autowired
   private SqlSession sqlSession;
   
   public int create(BlogVo blogVo) {
      sqlSession.insert("blog.insert",blogVo);
      return sqlSession.insert("blog.categoryInsert",blogVo); 
   }

   public BlogVo findAll(String id) {
      Map<String,Object> map = new HashMap<>();
      map.put("id", id);
      return sqlSession.selectOne("blog.findAll",map );
   }

   public int update(BlogVo blogVo) {
      return sqlSession.update("blog.update", blogVo);
   }

   public List<CategoryVo> getList(String id) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", id);
      return sqlSession.selectList("blog.findCategory",map);
   }

   public int Add(CategoryVo categoryVo) {
      return sqlSession.insert("blog.categoryAdd", categoryVo);
   }

   public int Delete(CategoryVo categoryVo) {
      return sqlSession.delete("blog.categoryDel",categoryVo);
   }

   public int writeAdd(PostVo postVo) {
      return sqlSession.insert("blog.post",postVo);
   }

   public List<PostVo> postList(String id, Long categoryNo, Long postNo) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", id);
      map.put("categoryNo", categoryNo);
      map.put("postNo", postNo);
      
      return sqlSession.selectList("blog.postSelect",map);
   }

   public List<PostVo> postMainList(Map<String, Object> map) {
      return sqlSession.selectList("blog.PostList",map);
   }

	public PostVo findPost(Map<String, Object> map) {
		return sqlSession.selectOne("blog.findPost", map);
	}

	public CategoryVo Recent(String id) {
		return sqlSession.selectOne("blog.recentList",id);
	}

}