package com.wooribank.blockchain.verifiablecredential.model;

import java.io.Serializable;
import java.util.List;

import com.wooribank.blockchain.verifiablecredential.util.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Member extends Paging implements Serializable {

	private static final long serialVersionUID = -1546272831912660975L;
	
	private int seqNo;    
	private String mbNm;   
	private String residNo;  
	private String mobileNo;
	private String joinDate; 
	
	private String issuedPlaceCd;
	private String issuedPlaceNm;
	private String certiCd;
	private String certiNm;
	
}
