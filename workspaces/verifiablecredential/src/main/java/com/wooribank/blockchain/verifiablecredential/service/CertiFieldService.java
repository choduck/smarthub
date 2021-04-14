package com.wooribank.blockchain.verifiablecredential.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wooribank.blockchain.verifiablecredential.dao.CertiFieldDao;
import com.wooribank.blockchain.verifiablecredential.model.CertiField;
import com.wooribank.blockchain.verifiablecredential.model.CertiList;
import com.wooribank.blockchain.verifiablecredential.model.Field;

/**
 * @FileName  	: CertiFieldService.java
 * @Description : 자격필드관리 Service 클래스
 */

@Service
public class CertiFieldService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private CertiFieldDao certiFieldDao;
	
	public int pageListCount(CertiList certiList) throws Exception {
		 return certiFieldDao.pageListCount(certiList);
	}
	
	public List<CertiList> pageList(CertiList certiList) throws Exception {
		 return certiFieldDao.pageList(certiList);
	}
	
	public void insert(CertiField certiField) throws Exception {
		try {
			certiFieldDao.insertCertiList(certiField);
			
			List<Field> fieldList = certiField.getFieldList();
			if (fieldList != null) {
				for (int i=0; i<fieldList.size(); i++) {
					if ( !"".equals(fieldList.get(i).getOrderNo()) ) {
						fieldList.get(i).setCertiCd(certiField.getCertiCd());
						
						certiFieldDao.insertCertiField(fieldList.get(i));
					}
				}
			}
		} catch(Exception e) {
			logger.info("Exception{}", e);
		}
	}
	
	public CertiField selectCertiList(CertiList certiList) throws Exception {
		 return certiFieldDao.selectCertiList(certiList); 
	}
	
	public List<Field> selectCertiField(CertiList certiList) throws Exception {
		 return certiFieldDao.selectCertiField(certiList); 
	}
	
	public int fieldTotalCount(CertiList certiList) throws Exception {
		 return certiFieldDao.fieldTotalCount(certiList);
	}
	
	public void update(CertiField certiField) throws Exception {
		
		try {
			List<Field> fieldList = certiField.getFieldList();
			certiFieldDao.updateCertiList(certiField);
			
			certiFieldDao.deleteCertiField(certiField);
			
			logger.info("# fieldList.size() : " + fieldList.size()); 
			
			if (fieldList != null) {
				for (int i=0; i<fieldList.size(); i++) {
					if ( !"".equals(fieldList.get(i).getOrderNo()) ) {
						fieldList.get(i).setCertiCd(certiField.getCertiCd());
					
						certiFieldDao.insertCertiField(fieldList.get(i));
					}
				}
			}
		} catch(Exception e) {
			logger.info("Exception{}", e);
		}
	}
	
	public void deleteCertiListField(CertiList certiList) throws Exception {
		 certiFieldDao.deleteCertiListField(certiList); 
	}
}
