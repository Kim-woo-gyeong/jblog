package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@RequestMapping({"","/{pathNo1}","/{pathNo1}/{pathNo2}"})
	public String main(
			@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			@PathVariable Optional<Long> pathNo1,
		    @PathVariable Optional<Long> pathNo2,
		    ModelMap modelMap,
		    Model model) {
		
		UserVo userVo = userService.getId(id);
		if(userVo == null) {
			return "/error/404";
		}
		
		 Long categoryNo = 0L;
	     Long postNo = 0L;
	      
	      if( pathNo2.isPresent() ) {
	         postNo = pathNo2.get();
	         categoryNo = pathNo1.get();
	      } else if( pathNo1.isPresent() ){
	    	  categoryNo = pathNo1.get();
	      }
	      
	      System.err.println("category:"+categoryNo);
	      System.err.println("post:"+postNo);
		
		GetBlogVo(id, model);
		
		List<CategoryVo> list = blogService.categoryList(id);
		
		modelMap.putAll( blogService.getAll( id, categoryNo, postNo ) );
		model.addAttribute("category", list);
		return "blog/main";
	}
	
	@RequestMapping(value="/admin/basic", method = RequestMethod.GET)
	public String basic(
			@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			Model model) {
		
		if(authUser == null || !id.equals(authUser.getId() )) {
			return "redirect:/user/login";
		}
		GetBlogVo(id, model);
		
		return "blog/basic";
	}
	
	@RequestMapping(value="/admin/update", method = RequestMethod.POST)
	public String update(
			@PathVariable("id") String id,
			@ModelAttribute BlogVo blogVo,
			@RequestParam("logo-file") MultipartFile multipartFile,
			Model model) {
		
		blogVo.setBlogID(id);
		String url = fileUploadService.restore(id, multipartFile);
		blogVo.setLogoURL(url);
		
		blogService.update(blogVo);
		model.addAttribute("blogVo", blogVo);
		return "redirect:/{id}";
	}
	
	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public String category(
			@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			Model model) {
		if(authUser == null || !id.equals(authUser.getId() )) {
			return "redirect:/user/login";
		}
		
		
		GetBlogVo(id, model);
		
		List<CategoryVo> list = blogService.categoryList(id);
		
		model.addAttribute("list", list);
		
		return "blog/category";
	}
	
	@RequestMapping(value = "/admin/categoryadd", method = RequestMethod.POST)
	public String categoryAdd(
			@PathVariable("id") String id,
			@ModelAttribute CategoryVo categoryVo,
			Model model) {
		categoryVo.setBlogID(id);
		
		blogService.Add(categoryVo);
		
		return "redirect:/{id}/admin/category";
	}
	
	@RequestMapping(value = "/admin/categorydel/{no}", method = RequestMethod.GET)
	public String categoryDel(
			@PathVariable("id") String id,
			@PathVariable("no") long no,
			@ModelAttribute CategoryVo categoryVo,
			Model model) {
		categoryVo.setBlogID(id);
		categoryVo.setNo(no);
		blogService.Delete(categoryVo);
		
		return "redirect:/{id}/admin/category";
	}
	
	@RequestMapping(value = "/admin/write", method = RequestMethod.GET)
	public String write(
			@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			Model model) {
		if(authUser == null || !id.equals(authUser.getId() )) {
			return "redirect:/user/login";
		}
		
		GetBlogVo(id, model);
		
		List<CategoryVo> list = blogService.categoryList(id);
		model.addAttribute("list", list);
		
		
		
		return "blog/write";
	}
	
	@RequestMapping(value = "/admin/writeadd", method = RequestMethod.POST)
	public String writeAdd(
			@PathVariable("id") String id,
			@ModelAttribute PostVo postVo,
			@RequestParam(value = "category", required = true, defaultValue = "") long no,
			Model model) {
		
		postVo.setCategoryNo(no);
		blogService.writeAdd(postVo);
		
		GetBlogVo(id, model);
		
		return "redirect:/{id}";
	}
	
	
	
	public void GetBlogVo(String id, Model model) {
	
		BlogVo blogVo = blogService.getBlog(id);
		model.addAttribute("blogVo", blogVo);
	}
}
