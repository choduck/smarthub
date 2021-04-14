package com.wooribank.blockchain.verifiablecredential.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wooribank.blockchain.verifiablecredential.model.CertiField;
import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.CommonCode;
import com.wooribank.blockchain.verifiablecredential.model.Member;
import com.wooribank.blockchain.verifiablecredential.model.MemberCertiField;
import com.wooribank.blockchain.verifiablecredential.service.CommonCodeService;
import com.wooribank.blockchain.verifiablecredential.service.MemberService;
import com.wooribank.blockchain.verifiablecredential.view.MessageView;

@Controller
@RequestMapping(value = {"/member"})
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private MemberService memberService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	@RequestMapping(value="/list.do",method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @ModelAttribute("member") Member member) throws Exception{
		ModelAndView mv = new ModelAndView();		
		
		member.setTotalCount(this.memberService.listCount(member));	
		
		mv.addObject("member", member);		
		mv.addObject("memberList", this.memberService.list(member));
		
		mv.setViewName("member/list");
		return mv;
	}
	
	@RequestMapping(value="/form.do", method=RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, @ModelAttribute("member") Member member)  throws Exception {	
		ModelAndView mv = new ModelAndView();	
		CommonCode commonCode = new CommonCode();

		// 발급처 코드
		commonCode.setUppCd("A0001");
		List<CommonCode> cdIssuedPlaceCdList = commonCodeService.codeList(commonCode);
		mv.addObject("cdIssuedPlaceCdList", cdIssuedPlaceCdList);
		
		// 자격증명 코드
		commonCode.setUppCd("B0001");
		List<CommonCode> cdCertiCdmList = commonCodeService.codeList(commonCode);
		mv.addObject("cdCertiCdList", cdCertiCdmList);
		
		int seqNo = member.getSeqNo();
		//수정 
		if(seqNo > 0){						
			member = this.memberService.selectMember(member);
			
			List<MemberCertiField> memberCertiFieldGrpList = this.memberService.selectMemberCertiFieldGrpList(member);
			mv.addObject("memberCertiFieldGrpList", memberCertiFieldGrpList);

			List<MemberCertiField> memberCertiFieldList = this.memberService.selectMemberCertiFieldList(member);
			mv.addObject("memberCertiFieldList", memberCertiFieldList);
		}
		
		mv.addObject("member", member);		
		mv.setViewName("member/form");
		
		return mv;	
	}
	
	@RequestMapping(value="/detail.do", method=RequestMethod.GET)
	public ModelAndView detail(HttpServletRequest request, @ModelAttribute("member") Member member)  throws Exception {	
		ModelAndView mv = new ModelAndView();	
		CommonCode commonCode = new CommonCode();

		// 발급처 코드
		commonCode.setUppCd("A0001");
		List<CommonCode> cdIssuedPlaceCdList = commonCodeService.codeList(commonCode);
		mv.addObject("cdIssuedPlaceCdList", cdIssuedPlaceCdList);
		
		// 자격증명 코드
		commonCode.setUppCd("B0001");
		List<CommonCode> cdCertiCdmList = commonCodeService.codeList(commonCode);
		mv.addObject("cdCertiCdList", cdCertiCdmList);
		
		int seqNo = member.getSeqNo();
		//수정 
		if(seqNo > 0){						
			member = this.memberService.selectMember(member);
			
			List<MemberCertiField> memberCertiFieldGrpList = this.memberService.selectMemberCertiFieldGrpList(member);
			mv.addObject("memberCertiFieldGrpList", memberCertiFieldGrpList);

			List<MemberCertiField> memberCertiFieldList = this.memberService.selectMemberCertiFieldList(member);
			mv.addObject("memberCertiFieldList", memberCertiFieldList);
		}
		
		mv.addObject("member", member);		
		mv.setViewName("member/detail");
		
		return mv;	
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/insert.do", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ModelAndView insert(HttpServletRequest request, 
			@ModelAttribute("member") Member member,
			Model model) throws Exception{	
		
		ModelAndView mv = new ModelAndView("customJsonView");	
		String resultCode = "success";
		String resultMsg  = "등록 되었습니다.";		
		
		try{	
			this.memberService.insert(member);
		} catch (Exception e){
			String message = "등록 중 오류가 발생하였습니다.";

			resultCode = "fail";
			resultMsg = message;
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("resultCode", resultCode);
		resultJson.put("resultMsg", resultMsg);
		resultJson.put("seqNo", member.getSeqNo());
		
		model.addAttribute("data", resultJson);
		
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/update.do", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ModelAndView update(HttpServletRequest request, 
			@ModelAttribute("member") Member member,
			Model model) throws Exception{	
		
		ModelAndView mv = new ModelAndView("customJsonView");	
		String resultCode = "success";
		String resultMsg  = "수정 되었습니다.";		
		
		try{	
			this.memberService.update(member);
		} catch (Exception e){
			String message = "수정 중 오류가 발생하였습니다.";

			resultCode = "fail";
			resultMsg = message;
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("resultCode", resultCode);
		resultJson.put("resultMsg", resultMsg);
		
		model.addAttribute("data", resultJson);
		
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/certiFieldList.do", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ModelAndView certiFieldList(HttpServletRequest request, 
			@ModelAttribute("certiList") CertiList certiList,
			Model model) throws Exception{	
		
		ModelAndView mv = new ModelAndView("customJsonView");	
		String resultCode = "success";
		String resultMsg  = "";		
		List<CertiField> fieldList = new ArrayList<CertiField>();
		JSONObject resultJson = new JSONObject();
		
		try{	
			fieldList = this.memberService.certiFieldList(certiList);
		} catch (Exception e){
			String message = "조회 중 오류가 발생하였습니다.";

			resultCode = "fail";
			resultMsg = message;
		}
		
		JSONArray jArray = new JSONArray();//배열이 필요할때
		
		for (int i = 0; i < fieldList.size(); i++) {
			JSONObject sObject = new JSONObject();
			sObject.put("issuedPlaceCd", fieldList.get(i).getIssuedPlaceCd());
			sObject.put("certiCd", fieldList.get(i).getCertiCd());
			sObject.put("certiNm", fieldList.get(i).getCertiNm());
			sObject.put("seqNo", fieldList.get(i).getSeqNo());
			sObject.put("orderNo", fieldList.get(i).getOrderNo());
			sObject.put("fieldKorNm", fieldList.get(i).getFieldKorNm());
			sObject.put("fieldNm", fieldList.get(i).getFieldNm());
			
			jArray.add(sObject);
	    }
		
		resultJson.put("resultCode", resultCode);
		resultJson.put("resultMsg", resultMsg);
		resultJson.put("rowCount", fieldList.size());
		resultJson.put("fieldList", jArray);
		
		model.addAttribute("data", resultJson);
		
		return mv;
	}
	
	@RequestMapping(value="/insertCertiField.do",method=RequestMethod.POST)
	public ModelAndView insertCertiField(HttpServletRequest request, @ModelAttribute("memberCertiField") MemberCertiField memberCertiField) throws Exception{	
		ModelAndView mv = new ModelAndView();	

		try{	
			this.memberService.insertCertiField(memberCertiField);	
			mv.setView(new MessageView("등록 되었습니다.", "" , "/member/list.do"));
		} catch (Exception e){
			String message = e.getMessage();
			
			if(message == null || "".equals(message)){
				message = "등록하는 중 오류가 발생하였습니다.";
			}
			
			mv.setView(new MessageView(message , "history.go(-1);" , ""));				
		}

		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deletememberCertiField.do", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ModelAndView deletememberCertiField(HttpServletRequest request, 
			@ModelAttribute("memberCertiField") MemberCertiField memberCertiField,
			Model model) throws Exception {		
		ModelAndView mv = new ModelAndView("customJsonView");	
		String resultCode = "success";
		String resultMsg  = "삭제 되었습니다.";		
		
		try{	
			this.memberService.deleteMemberCertiField(memberCertiField);
		} catch (Exception e){
			String message = "삭제 중 오류가 발생하였습니다.";

			resultCode = "fail";
			resultMsg = message;
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("resultCode", resultCode);
		resultJson.put("resultMsg", resultMsg);
		
		model.addAttribute("data", resultJson);
		
		return mv;
	}	
	
	@RequestMapping(value="/deleteAbanMember.do",method=RequestMethod.POST)
	public ModelAndView deleteAbanMember(
			HttpServletRequest request, 
			@ModelAttribute("member") Member member) throws Exception{	
		
		ModelAndView mv = new ModelAndView();	

		try{	
			this.memberService.deleteAbanMember(member);		
			mv.setView(new MessageView("폐기 되었습니다.", "" , "/member/list.do"));
		
		} catch (Exception e){
			String message = "폐기 중 오류가 발생하였습니다.";
			mv.setView(new MessageView(message , "history.go(-1);" , ""));				
		}

		return mv;
	}	
	
}
