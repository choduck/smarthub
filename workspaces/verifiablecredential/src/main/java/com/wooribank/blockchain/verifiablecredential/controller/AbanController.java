package com.wooribank.blockchain.verifiablecredential.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wooribank.blockchain.verifiablecredential.model.Aban;
import com.wooribank.blockchain.verifiablecredential.service.AbanService;

@Controller
@RequestMapping(value = {"/aban"})
public class AbanController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private AbanService abanService;
	
	@RequestMapping(value="/list.do",method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @ModelAttribute("aban") Aban aban) throws Exception{
		ModelAndView mv = new ModelAndView();		
		
		aban.setTotalCount(this.abanService.listCount(aban));	
		
		mv.addObject("aban", aban);		
		mv.addObject("abanList", this.abanService.list(aban));
		
		mv.setViewName("aban/list");
		return mv;
	}
	
}
