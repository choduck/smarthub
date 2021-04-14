package com.wooribank.blockchain.verifiablecredential.model;


import java.io.Serializable;

import com.wooribank.blockchain.verifiablecredential.util.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CertiList extends Paging implements Serializable {

	private static final long serialVersionUID = 656320140407350134L;
	
	private int seqNo;
	private String issuedPlaceCd;
	private String issuedPlaceNm;
	private String certiCd;
	private String certiNm;
}
