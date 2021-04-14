package com.wooribank.blockchain.verifiablecredential.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wooribank.blockchain.verifiablecredential.dao.MemberDao;
import com.wooribank.blockchain.verifiablecredential.model.CertiField;
import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.Member;
import com.wooribank.blockchain.verifiablecredential.model.MemberCertiField;

@Service
public class MemberService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private MemberDao memberDao;
 
	public int listCount(Member member) throws Exception {
		 return memberDao.listCount(member);
	}
	
	public List<Member> list(Member member)  throws Exception {
		 return memberDao.list(member);
	}
	
	public void insert(Member member)  throws Exception {
		memberDao.insert(member);
	}
	
	public Member selectMember(Member member) throws Exception {
		 return memberDao.selectMember(member);
	}
	
	public List<MemberCertiField> selectMemberCertiFieldGrpList(Member member) throws Exception {
		 return memberDao.selectMemberCertiFieldGrpList(member);
	}
	
	public List<MemberCertiField> selectMemberCertiFieldList(Member member) throws Exception {
		 return memberDao.selectMemberCertiFieldList(member);
	}
	
	public void update(Member member)  throws Exception {
		memberDao.update(member);
	}
	
	public List<CertiField> certiFieldList(CertiList certiList) throws Exception {
		 return memberDao.selectCertiFieldList(certiList);
	}
	
	public void insertCertiField(MemberCertiField memberCertiField)  throws Exception {
		try {
			String[] arryResidNo = memberCertiField.getArryResidNo();
			String[] arryIssuedPlaceCd = memberCertiField.getArryIssuedPlaceCd();
			String[] arryCertiCd = memberCertiField.getArryCertiCd();
			String[] arryFieldNm = memberCertiField.getArryFieldNm();
			String[] arryFieldKorNm = memberCertiField.getArryFieldKorNm();
			String[] arryFieldCnt = memberCertiField.getArryFieldCnt();
			
			if (arryResidNo != null) {
				for (int i=0; i < arryResidNo.length; i++) {
					memberCertiField.setResidNo(arryResidNo[i]);
					memberCertiField.setIssuedPlaceCd(arryIssuedPlaceCd[i]);
					memberCertiField.setCertiCd(arryCertiCd[i]);
					
					memberDao.deleteMemberCertiField(memberCertiField);
				}					
				
				for (int i=0; i < arryResidNo.length; i++) {
					memberCertiField = new MemberCertiField();
					
					memberCertiField.setResidNo(arryResidNo[i]);
					memberCertiField.setIssuedPlaceCd(arryIssuedPlaceCd[i]);
					memberCertiField.setCertiCd(arryCertiCd[i]);
					memberCertiField.setFieldNm(arryFieldNm[i]);
					memberCertiField.setFieldKorNm(arryFieldKorNm[i]);
					memberCertiField.setFieldCnt(arryFieldCnt[i]);
					
					memberDao.insertCertiField(memberCertiField);
				}
			}
		} catch(Exception e) {
			logger.info("Exception{}", e);
		}
	}
	
	public void deleteMemberCertiField(MemberCertiField memberCertiField)  throws Exception {
		memberDao.deleteMemberCertiField(memberCertiField);
	}
	
	public void deleteAbanMember(Member member)  throws Exception {
		memberDao.insertAban(member);
		memberDao.deleteAbanMemberCertiField(member);
		memberDao.deleteAbanMember(member);
	}
}
