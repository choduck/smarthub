package com.wooribank.blockchain.verifiablecredential.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wooribank.blockchain.verifiablecredential.model.CertiField;
import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.CommonCode;
import com.wooribank.blockchain.verifiablecredential.model.Field;
import com.wooribank.blockchain.verifiablecredential.service.CertiFieldService;
import com.wooribank.blockchain.verifiablecredential.service.CommonCodeService;
import com.wooribank.blockchain.verifiablecredential.view.MessageView;

/**
 * @FileName  	: CommonCodeService.java
 * @Description : 공통 코드 Controller 클래스
 */

@Controller
@RequestMapping("/certiField")
public class CertiFieldController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private CertiFieldService certiFieldService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @ModelAttribute("certiList") CertiList certiList) throws Exception{
		ModelAndView mv = new ModelAndView();	
		
		//갯수조회
		certiList.setTotalCount(this.certiFieldService.pageListCount(certiList));
		
		//리스트조회
		List<CertiList> certiArrayList =  this.certiFieldService.pageList(certiList);
		
		mv.addObject("certiList", certiList);
		mv.addObject("certiArrayList", certiArrayList);
		
		mv.setViewName("certiField/list"); 
		
		return mv;
    }
	
	@RequestMapping(value="/form.do", method=RequestMethod.GET)
	public ModelAndView form( HttpServletRequest request, @ModelAttribute("certiList") CertiList certiList) throws Exception{
		ModelAndView mv = new ModelAndView();	
		CommonCode commonCode = new CommonCode();
		CertiField certiField = new CertiField();
		
		// 발급처 코드
		commonCode.setUppCd("A0001");
		List<CommonCode> cdIssuedPlaceCdList = commonCodeService.codeList(commonCode);
		mv.addObject("cdIssuedPlaceCdList", cdIssuedPlaceCdList);
		
		// 자격증명 코드
		commonCode.setUppCd("B0001");
		List<CommonCode> cdCertiCdmList = commonCodeService.codeList(commonCode);
		mv.addObject("cdCertiCdList", cdCertiCdmList);
		
		int seqNo = certiList.getSeqNo();			
		 
		//수정 
		if (seqNo > 0) {			
			certiField = this.certiFieldService.selectCertiList(certiList);
			
			certiList.setCertiCd(certiField.getCertiCd());
			List<Field> fieldList = this.certiFieldService.selectCertiField(certiList);
			mv.addObject("fieldList", fieldList);
			
			mv.addObject("fieldTotalCount", this.certiFieldService.fieldTotalCount(certiList));
		}
		
		mv.addObject("certiField", certiField);
		mv.setViewName("certiField/form");
		
		return mv;
	}
	
	@RequestMapping(value="/ajax/certiFieldForm.do", method=RequestMethod.GET)
	public ModelAndView certiFieldForm(HttpServletRequest request) throws Exception{	
		ModelAndView mv = new ModelAndView();	

		mv.addObject("maxCount", request.getParameter("maxCount"));	
		mv.setViewName("certiField/ajax/certiFieldForm");
		
		return mv;	
	}
	
	@RequestMapping(value="/insert.do",method=RequestMethod.POST)
	public ModelAndView insert(HttpServletRequest request, @ModelAttribute("certiField") CertiField certiField) throws Exception{	
		ModelAndView mv = new ModelAndView();	

		try{	
			this.certiFieldService.insert(certiField);	
			mv.setView(new MessageView("등록 되었습니다.", "" , "/certiField/list.do"));
		} catch (Exception e){
			String message = e.getMessage();
			
			if(message == null || "".equals(message)){
				message = "등록하는 중 오류가 발생하였습니다.";
			}
			
			mv.setView(new MessageView(message , "history.go(-1);" , ""));				
		}

		return mv;
	}
	
	@RequestMapping(value="/update.do",method=RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, @ModelAttribute("certiField") CertiField certiField) throws Exception{	
		
		ModelAndView mv = new ModelAndView();	
		
		try{	
			this.certiFieldService.update(certiField);
			
			mv.setView(new MessageView("수정 되었습니다.", "" , "/certiField/list.do"));
		} catch (Exception e){
			String message = e.getMessage();
			
			if(message == null || "".equals(message)){
				message = "수정 중 오류가 발생하였습니다.";
			}
		
			mv.setView(new MessageView(message , "history.go(-1);" , ""));				
		}

		return mv;
	}
	
	@RequestMapping(value="/deleteCertiListField.do", method=RequestMethod.POST)
	public ModelAndView deleteCertiListField(HttpServletRequest request, @ModelAttribute("certiList") CertiList certiList) throws Exception{	
		
		ModelAndView mv = new ModelAndView();	

		try{	
			this.certiFieldService.deleteCertiListField(certiList);		
			mv.setView(new MessageView("삭제 되었습니다.", "" , "/certiField/list.do"));
		} catch (Exception e){
			String message = e.getMessage();
			
			if(message == null || "".equals(message)){
				message = "삭제 중 오류가 발생하였습니다.";
			}
		
			mv.setView(new MessageView(message , "history.go(-1);" , ""));				
		}

		return mv;
	}	
}
