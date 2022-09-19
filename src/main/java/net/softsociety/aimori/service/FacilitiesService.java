package net.softsociety.aimori.service;

import net.softsociety.aimori.domain.Facilities;
import net.softsociety.aimori.domain.FacilitiesValuation;

public interface FacilitiesService {

	/**
	 * parameter와 일치하는 시설이 있는지 확인하는 메소드
	 * @param facilities
	 * @return Facilities
	 */
	Facilities findFacilities(Facilities facilities);

	/**
	 * parameter로 받은 시설 정보를 facilities테이블에 넣는 메소드
	 * @param facilities
	 * @return 0 || 1
	 */
	int insertFacilities(Facilities facilities);

	/**
	 * parameter로 전달받은 시설의 DB등록 번호를 찾는 메소드
	 * @param facilities
	 * @return 시설 DB저장 번호(시퀀스 번호)
	 */
	int findFacilitiesNumber(Facilities facilities);
	

	/**
	 * FacilitiesValuation 테이블에 값을 넣는 메소드
	 * @param fv
	 * @return 0 || 1
	 */
	int insertFacilitiesReview(FacilitiesValuation fv);

	/**
	 * 시설의 평점을 찾는 메소드
	 * @param facilitesNumber
	 * @return 시설 평점
	 */
	double findFacilitiesStar(int facilitesNumber);
	
	/**
	 * 시설을 평가한 사람의 수를 세는 메소드
	 * @param facilitesNumber
	 * @return 해당 시설을 평가한 사람 수
	 */
	int countFaciliteisStar(int facilitesNumber);
}
