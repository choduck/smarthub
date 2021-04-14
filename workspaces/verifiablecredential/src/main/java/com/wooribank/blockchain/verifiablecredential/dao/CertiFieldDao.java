package com.wooribank.blockchain.verifiablecredential.dao;

import java.util.List;

import com.wooribank.blockchain.verifiablecredential.model.CertiField;
import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.Field;

/**
 * @FileName  	: CertiFieldDao.java
 * @Description : 자격필드관리Dao 인터페이스
 */
public interface CertiFieldDao {	
	public int pageListCount(CertiList certiList);
	
	public List<CertiList> pageList(CertiList certiList);
	
	public void insertCertiList(CertiField certiField);
	
	public void insertCertiField(Field field);

	public CertiField selectCertiList(CertiList certiList);
	
	public List<Field> selectCertiField(CertiList certiList);

	public int fieldTotalCount(CertiList certiList);
	
	public void updateCertiList(CertiField certiField);
	
	public void deleteCertiField(CertiField certiField);
	
	public void deleteCertiListField(CertiList certiList) throws Exception;
	
}
