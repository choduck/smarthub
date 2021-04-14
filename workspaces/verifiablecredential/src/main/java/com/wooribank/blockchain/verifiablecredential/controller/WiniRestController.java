package com.wooribank.blockchain.verifiablecredential.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.CommonCode;
import com.wooribank.blockchain.verifiablecredential.model.Field;
import com.wooribank.blockchain.verifiablecredential.service.CertiFieldService;
import com.wooribank.blockchain.verifiablecredential.service.CommonCodeService;

@RestController
@RequestMapping(value={"rest/wini/"})
public class WiniRestController {
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@Autowired
	private CertiFieldService certiFieldService;
	
	// 발급처 선택
	@RequestMapping(value="/issuePlaceList.do", method=RequestMethod.POST)
	public List issueList(HttpServletRequest request, @ModelAttribute("commonCode") CommonCode commonCode) throws Exception{
		
		// 세션생성에 sid값 12345로 픽스
		request.getSession().setAttribute("sid", "12345");
		
		if("".equals(commonCode.getUppCd())){
			commonCode.setUppCd("A0001");
		}
		
		// 발급처 코드
		List<CommonCode> cdIssuedPlaceCdList = commonCodeService.codeList(commonCode);
		return cdIssuedPlaceCdList;
	}
	
	// 신분증, 자격증명 선택 certiList
	@RequestMapping(value="/certiList.do", method=RequestMethod.POST)
	public List certiList(HttpServletRequest request, @ModelAttribute("commonCode") CommonCode commonCode) throws Exception{
		
		if("".equals(commonCode.getUppCd()) || "A0001".equals(commonCode.getUppCd())){
			commonCode.setUppCd("B0001");
		}
		List<CommonCode> cdCertiCdmList = commonCodeService.codeList(commonCode);
		
		
		return cdCertiCdmList;
	}
	
	// 연동 정보 선택
	@RequestMapping(value="/certiFieldList.do", method=RequestMethod.POST)
	public List syncList(HttpServletRequest request, @ModelAttribute("certiList") CertiList certiList) throws Exception{
		List<Field> selectCertiField = certiFieldService.selectCertiField(certiList);
		return selectCertiField;
	}
}
