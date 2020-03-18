package com.douzone.jblog.service;

import java.util.List;

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

	public void Delete(CategoryVo categoryVo) {
		blogRepository.Delete(categoryVo);
	}

	public void writeAdd(PostVo postVo) {
		blogRepository.writeAdd(postVo);
	}

}
