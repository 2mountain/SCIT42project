package net.softsociety.aimori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.FacilitiesDAO;
import net.softsociety.aimori.domain.Facilities;
import net.softsociety.aimori.domain.FacilitiesValuation;

@Slf4j
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

	@Override
	public int findFacilitiesNumber(Facilities facilities) {
		log.debug("[FacilitiesServiceImpl] findFacilitiesNumber - parameter : {}", facilities);
		int result = dao.findFacilitiesNumber(facilities);
		return result;
	}

	@Override
	public int insertFacilitiesReview(FacilitiesValuation fv) {
		int result = dao.insertFacilitiesReview(fv);
		return result;
	}

	@Override
	public double findFacilitiesStar(int facilitesNumber) {
		
		// 리뷰 작성자가 10명 미만인 경우 평점 출력 X
		if(countFaciliteisStar(facilitesNumber) < 10) {
			return 99.0;
		}
		
		double result = dao.findFacilitiesStar(facilitesNumber);
		return result;
	}

	@Override
	public int countFaciliteisStar(int facilitesNumber) {
		int result = dao.countFaciliteisStar(facilitesNumber);
		return result;
	}

	@Override
	public List<FacilitiesValuation> getFacilitiesReview(int facilitiesNumber) {
		List<FacilitiesValuation> fv = dao.getFacilitiesReview(facilitiesNumber);
		return fv;
	}

}
