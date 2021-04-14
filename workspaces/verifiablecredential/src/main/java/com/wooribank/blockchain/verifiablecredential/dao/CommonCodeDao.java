package com.wooribank.blockchain.verifiablecredential.dao;

import java.util.List;

import com.wooribank.blockchain.verifiablecredential.model.CommonCode;

/**
 * @FileName  	: CommonCodeDao.java
 * @Description : 공통 코드 Dao 인터페이스
 */
public interface CommonCodeDao {
	public List<CommonCode> codeList(CommonCode commonCode);
}
