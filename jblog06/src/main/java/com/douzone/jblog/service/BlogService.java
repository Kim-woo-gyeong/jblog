package com.douzone.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class BlogService {

   @Autowired
   private BlogRepository blogRepository;
   
   public Boolean create(UserVo vo) {
      BlogVo blogVo = new BlogVo();
      
      blogVo.setBlogID(vo.getId());
      blogVo.setTitle(vo.getName() + "님의 블로그입니다. ");
      blogVo.setLogoURL("/assets/images/spring-logo.jpg");
      
      int count = blogRepository.create(blogVo);
      return count == 1;
   }

   public BlogVo getBlog(String id) {
      return blogRepository.findAll(id);
   }

   public Boolean update(BlogVo blogVo) {
      int count = blogRepository.update(blogVo);
      return count == 1;
   }

   public List<CategoryVo> categoryList(String id) {
      return blogRepository.getList(id);
   }

   public void Add(CategoryVo categoryVo) {
	   blogRepository.Add(categoryVo);
   }

   public boolean Delete(CategoryVo categoryVo) {
      return 1 == blogRepository.Delete(categoryVo);
   }

   public void writeAdd(PostVo postVo) {
      blogRepository.writeAdd(postVo);
   }

   public List<PostVo> postList(String id, Long categoryNo, Long postNo) {
      return blogRepository.postList(id, categoryNo, postNo);
   }

   public Map<String, Object> getAll(String id, Long categoryNo, Long postNo) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", id);
      map.put("categoryNo", categoryNo);
      map.put("postNo", postNo);
      
      List<PostVo> postList = blogRepository.postMainList(map);
      map.put("postList",postList);
      
      PostVo post = blogRepository.findPost(map);
      map.put("post",post);
      
      System.err.println("service : " + postList);
      return map;
   }

	public void RecentAdd(String id) {
		blogRepository.Recent(id);
	}

}