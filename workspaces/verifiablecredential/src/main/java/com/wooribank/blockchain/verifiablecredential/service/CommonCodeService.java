package com.wooribank.blockchain.verifiablecredential.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wooribank.blockchain.verifiablecredential.dao.CommonCodeDao;
import com.wooribank.blockchain.verifiablecredential.model.CommonCode;

/**
 * @FileName  	: CommonCodeService.java
 * @Description : 공통 코드 Service 클래스
 */

@Service
public class CommonCodeService {
	@Autowired 
	private CommonCodeDao commonCodeDao;
	
	public List<CommonCode> codeList(CommonCode commonCode) {
		return commonCodeDao.codeList(commonCode);
	}
}
