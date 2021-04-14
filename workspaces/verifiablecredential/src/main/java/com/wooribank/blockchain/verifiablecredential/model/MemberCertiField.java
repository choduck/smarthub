package com.wooribank.blockchain.verifiablecredential.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MemberCertiField implements Serializable {

	private static final long serialVersionUID = -7409288853165579262L;
	
	private int seqNo;    
	private String residNo;    
	private String issuedPlaceCd;
	private String issuedPlaceNm;
	private String certiCd;
	private String certiNm;
	private String fieldNm;
	private String fieldKorNm;
	private String fieldCnt;  
	
	private String[] arryResidNo;    
	private String[] arryIssuedPlaceCd;
	private String[] arryCertiCd;
	private String[] arryFieldNm;
	private String[] arryFieldKorNm;
	private String[] arryFieldCnt;   
}
