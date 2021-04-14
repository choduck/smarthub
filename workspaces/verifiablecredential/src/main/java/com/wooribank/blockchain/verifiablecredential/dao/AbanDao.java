package com.wooribank.blockchain.verifiablecredential.dao;

import java.util.List;

import com.wooribank.blockchain.verifiablecredential.model.Aban;

public interface AbanDao {	
	public int listCount(Aban aban);
	public List<Aban> list(Aban aban);
}
