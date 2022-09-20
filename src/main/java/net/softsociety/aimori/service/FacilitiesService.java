package net.softsociety.aimori.service;

import java.util.List;

import net.softsociety.aimori.domain.Facilities;
import net.softsociety.aimori.domain.FacilitiesValuation;

public interface FacilitiesService {

	/**
	 * parameter와 일치하는 시설이 있는지 확인하는 메소드
	 * @param facilities
	 * @return Facilities
	 */
	public Facilities findFacilities(Facilities facilities);

	/**
	 * parameter로 받은 시설 정보를 facilities테이블에 넣는 메소드
	 * @param facilities
	 * @return 0 || 1
	 */
	public int insertFacilities(Facilities facilities);

	/**
	 * parameter로 전달받은 시설의 DB등록 번호를 찾는 메소드
	 * @param facilities
	 * @return 시설 DB저장 번호(시퀀스 번호)
	 */
	public int findFacilitiesNumber(Facilities facilities);
	

	/**
	 * FacilitiesValuation 테이블에 값을 넣는 메소드
	 * @param fv
	 * @return 0 || 1
	 */
	public int insertFacilitiesReview(FacilitiesValuation fv);

	/**
	 * 시설의 평점을 찾는 메소드
	 * @param facilitesNumber
	 * @return 시설 평점
	 */
	public double findFacilitiesStar(int facilitesNumber);
	
	/**
	 * 시설을 평가한 사람의 수를 세는 메소드
	 * @param facilitesNumber
	 * @return 해당 시설을 평가한 사람 수
	 */
	public int countFaciliteisStar(int facilitesNumber);

	/**
	 * 시설 번호로 해당 시설의 리뷰를 가져오는 메소드
	 * @param facilitiesNumber
	 * @return 시설 리뷰
	 */
	public List<FacilitiesValuation> getFacilitiesReview(int facilitiesNumber);

	/**
	 * parameter의 리뷰번호와 일치하는 리뷰 삭제
	 * @param facilitiesEvaluationNumber
	 * @return 0 || 1
	 */
	public int deleteFacilitiesReview(int facilitiesEvaluationNumber);

	/**
	 * 해당 유저의 rolename을 확인하는 메소드
	 * @param username
	 * @return rolename
	 */
	public String checkRole(String username);

	/**
	 * 해당 시설 리뷰 작성자의 아이디 조회
	 * @param facilitiesEvaluationNumber
	 * @return 시설 리뷰 작성자의 아이디
	 */
	public String checkReviewWrite(int facilitiesEvaluationNumber);
}
