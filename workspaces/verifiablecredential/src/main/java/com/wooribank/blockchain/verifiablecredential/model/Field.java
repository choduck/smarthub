package com.wooribank.blockchain.verifiablecredential.model;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Field implements Serializable {

	private static final long serialVersionUID = -3032634994330684628L;
	
	private int seqNo;
	private String certiCd;
	private String certiNm;
	private int orderNo;
	private String fieldNm;
	private String fieldKorNm;
	private String dataTp;
	private String desctn;
}
