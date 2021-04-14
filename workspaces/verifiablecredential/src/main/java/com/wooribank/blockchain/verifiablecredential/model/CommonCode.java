package com.wooribank.blockchain.verifiablecredential.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @FileName  	: CommonCode.java
 * @Description : 공통 코드
 */
@Data
public class CommonCode implements Serializable{

	private static final long serialVersionUID = 6524508237082693721L;
	
	//테이블 정보 
	private String cd;			//코드
	private String uppCd;		//상위코드
	private String cdNm;		//코드명
	private String level;		//코드레벨
	private String orderNo;		//코드순서
	private String useFl;		//사용여부	
}
