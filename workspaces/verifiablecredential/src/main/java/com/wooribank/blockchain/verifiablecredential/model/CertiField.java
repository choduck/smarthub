package com.wooribank.blockchain.verifiablecredential.model;


import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CertiField implements Serializable {

	private static final long serialVersionUID = 8020732298154030860L;
	
	private int seqNo;
	private String issuedPlaceCd;
	private String issuedPlaceNm;
	private String certiCd;
	private String certiNm;
	private Integer orderNo;
	private String fieldNm;
	private String fieldKorNm;
	private String dataTp;
	private String desctn;
	
	private List<Field> fieldList;
}
