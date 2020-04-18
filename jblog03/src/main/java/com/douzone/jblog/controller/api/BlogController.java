package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.AuthUser;

@RestController("BlogApiController")
@RequestMapping("/{id:(?!assets).*}/api")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/admin/category")
	public JsonResult list(
			@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			Model model) {
		List<CategoryVo> list = blogService.categoryList(id);
		return JsonResult.success(list);
	}
	
	@PostMapping("/admin/categoryadd")
	public JsonResult add(
			@PathVariable("id") String id, 
			@RequestBody CategoryVo categoryVo) {
		
		categoryVo.setBlogID(id);
		blogService.Add(categoryVo);
		return JsonResult.success(categoryVo);
	}
	
	@DeleteMapping("/admin/categorydel/{no}")
	public JsonResult delete(
			@PathVariable("id") String id,
			@PathVariable("no") long no) {
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setNo(no);
		categoryVo.setBlogID(id);
		System.out.println("conroller" + no +"----");
		boolean result = blogService.Delete(categoryVo);
		
		return JsonResult.success(result ? no : -1);
	}
	
	public void SpanBlogVo(String id, Model model) {
		BlogVo blogVo = blogService.getBlog(id);
		model.addAttribute("blogVo", blogVo);
	}
}
