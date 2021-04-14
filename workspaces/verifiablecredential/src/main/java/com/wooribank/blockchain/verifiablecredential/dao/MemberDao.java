package com.wooribank.blockchain.verifiablecredential.dao;

import java.util.List;

import com.wooribank.blockchain.verifiablecredential.model.CertiField;
import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.Member;
import com.wooribank.blockchain.verifiablecredential.model.MemberCertiField;

public interface MemberDao {	
	public int listCount(Member member);
	
	public List<Member> list(Member member);
	
	public void insert(Member member);
	
	public Member selectMember(Member member);
	
	public List<MemberCertiField> selectMemberCertiFieldGrpList(Member member);

	public List<MemberCertiField> selectMemberCertiFieldList(Member member);
	
	public void update(Member member);
	
	public List<CertiField> selectCertiFieldList(CertiList certiList);
	
	public void insertCertiField(MemberCertiField memberCertiField);

	public void deleteMemberCertiField(MemberCertiField memberCertiField);
	
	public void insertAban(Member member);

	public void deleteAbanMember(Member member);
	
	public void deleteAbanMemberCertiField(Member member);

}
