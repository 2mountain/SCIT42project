package net.softsociety.aimori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	// 시설 리뷰가 5개 미만이면 평점 return X
	@Value("${user.facilities.count}")
	int countReview;
	
	@Autowired
	FacilitiesDAO dao;
	
	@Override
	public Facilities findFacilities(Facilities facilities) {
		log.debug("findFacilities");
		Facilities facility = dao.findFacilities(facilities);
		
		return facility;
	}

	@Override
	public int insertFacilities(Facilities facilities) {
		log.debug("insertFacilities");
		int result = dao.insertFacilities(facilities);
		return result;
	}

	@Override
	public int findFacilitiesNumber(Facilities facilities) {
		log.debug("findFacilitiesNumber");
		log.debug("[FacilitiesServiceImpl] findFacilitiesNumber - parameter : {}", facilities);
		int result = dao.findFacilitiesNumber(facilities);
		return result;
	}

	@Override
	public int insertFacilitiesReview(FacilitiesValuation fv) {
		log.debug("insertFacilitiesReview");
		int result = dao.insertFacilitiesReview(fv);
		return result;
	}

	@Override
	public double findFacilitiesStar(int facilitesNumber) {
		log.debug("findFacilitiesStar");
		log.debug("findFacilitiesStar - countReview : {}", countReview);
		// 리뷰 작성자가 5명 미만인 경우 평점 출력 X
		if(countFaciliteisStar(facilitesNumber) < countReview) {
			return 99.0;
		}
		
		double result = dao.findFacilitiesStar(facilitesNumber);
		return result;
	}

	@Override
	public int countFaciliteisStar(int facilitesNumber) {
		log.debug("countFaciliteisStar");
		int result = dao.countFaciliteisStar(facilitesNumber);
		return result;
	}

	@Override
	public List<FacilitiesValuation> getFacilitiesReview(int facilitiesNumber) {
		log.debug("getFacilitiesReview");
		List<FacilitiesValuation> fv = dao.getFacilitiesReview(facilitiesNumber);
		return fv;
	}

	@Override
	public int deleteFacilitiesReview(int facilitiesEvaluationNumber) {
		log.debug("deleteFacilitiesReview");
		int result = dao.deleteFacilitiesReview(facilitiesEvaluationNumber);
		return result;
	}

	@Override
	public String checkRole(String username) {
		log.debug("checkRole");
		String result = dao.checkRole(username);
		return result;
	}

	@Override
	public String checkReviewWrite(int facilitiesEvaluationNumber) {
		log.debug("checkReviewWrite");
		String result = dao.checkReviewWrite(facilitiesEvaluationNumber);
		return result;
	}
}
