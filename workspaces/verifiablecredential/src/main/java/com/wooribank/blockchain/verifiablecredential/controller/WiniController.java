package com.wooribank.blockchain.verifiablecredential.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"wini/"})
public class WiniController {
	
	@RequestMapping(value="/main.do",method=RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wini/main");
		return mv;
	}
	
	
	
}
