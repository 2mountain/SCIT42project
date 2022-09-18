package net.softsociety.aimori.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.softsociety.aimori.dao.FacilitiesDAO;
import net.softsociety.aimori.domain.Facilities;

@Transactional
@Service
public class FacilitiesServiceImpl implements FacilitiesService {
	@Autowired
	FacilitiesDAO dao;
	
	@Override
	public Facilities findFacilities(Facilities facilities) {
		Facilities facility = dao.findFacilities(facilities);
		
		return facility;
	}

	@Override
	public int insertFacilities(Facilities facilities) {
		int result = dao.insertFacilities(facilities);
		return result;
	}

}
