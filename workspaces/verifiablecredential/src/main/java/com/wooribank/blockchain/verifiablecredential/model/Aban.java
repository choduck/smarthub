package com.wooribank.blockchain.verifiablecredential.model;

import java.io.Serializable;

import com.wooribank.blockchain.verifiablecredential.util.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Aban extends Paging implements Serializable {

	private static final long serialVersionUID = -8962883036626740776L;
	
	private int seqNo;    
	private String mbNm;    
	private String certiCd;   
	private String certiNm;   
	private String cid;   
	private String abanDate;   
}
