package com.wooribank.blockchain.verifiablecredential.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wooribank.blockchain.verifiablecredential.dao.AbanDao;
import com.wooribank.blockchain.verifiablecredential.model.Aban;

@Service
public class AbanService{

	@Autowired 
	private AbanDao abanDao;
 
	public int listCount(Aban Aban) throws Exception {
		 return abanDao.listCount(Aban);
	}
	
	public List<Aban> list(Aban Aban)  throws Exception {
		 return abanDao.list(Aban);
	}
}
