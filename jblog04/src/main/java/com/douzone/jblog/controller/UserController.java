package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(vo);
		blogService.create(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
//	@Auth
//	@RequestMapping(value="/update", method=RequestMethod.GET)
//	public String update(
//			@AuthUser UserVo authUser, Model model) {
//
//		
//		Long no = authUser.getNo();
//		
//		UserVo vo = userService.findUser(no);
//		model.addAttribute("vo", vo);
//		return "user/update";
//	}
	
//	@Auth
//	@RequestMapping(value="/update/{bool}", method=RequestMethod.POST)
//	public String userUpdate(
//			@PathVariable("bool") boolean bool, 
//			Model model, 
//			@AuthUser UserVo authUser, UserVo vo) {
//
//		vo.setNo(authUser.getNo());
//		model.addAttribute("bool", bool);
//		
//		userService.update(vo);
//		authUser.setName(vo.getName());
//		return "user/joinsuccess";
//	}
}
