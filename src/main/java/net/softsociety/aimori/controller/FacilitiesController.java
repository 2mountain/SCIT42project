package net.softsociety.aimori.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Facilities;
import net.softsociety.aimori.domain.FacilitiesValuation;
import net.softsociety.aimori.service.FacilitiesService;

@Slf4j
@RequestMapping("facilities")
@Controller
public class FacilitiesController {
	
	// 별점은 5가 최대
	@Value("${user.facilities.star}")
	int star;
	
	@Autowired
	FacilitiesService service;
	
	@GetMapping({"", "/"})
	public String facilities(Model model) {
		
		// 이부분에다가 값을 바꿔주면 처음 띄울 때 값이 바뀐다!
		String place = "애완동물";
		int radius = 3000;
		 
		model.addAttribute("place", place);
		model.addAttribute("radius", radius);
		
		log.debug("[FacilitiesController] facilities");
		
		return "facilities/facilities";
	}
	
	@PostMapping({"", "/"})
	public String placeSave(Model model, 
			@RequestParam(name="place", defaultValue="애완동물") String place,
			@RequestParam(name="radius", defaultValue="3000") int radius) {
		log.debug("[FacilitiesController] placeSave - parameter : {}", place);
		log.debug("[FacilitiesController] placeSave - parameter : {}", radius);

		model.addAttribute("place", place);
		model.addAttribute("radius", radius);
		
		return "facilities/facilities";
	}
	
	// 시설 선택시 해당 시설이 DB에 있는지 확인 / 있다면 해당 시설 리뷰 return / 없다면 해당 시설 DB에 insert
	@ResponseBody
	@PostMapping("/findFacilitiesData")
	public List<FacilitiesValuation> findFacilitiesData(Facilities facilities) {
		log.debug("[FacilitiesController] findFacilitiesData - parameter : {}", facilities);

		// parameter와 일치하는 시설이 있는지 확인하는 메소드
		Facilities facility = service.findFacilities(facilities);
		log.debug("[FacilitiesController] findFacilitiesData - findFacilitiesData - result : {}", facility);
		
		
		// 시설의 정보가 이미 DB에 있는 경우, 리뷰를 불러와 담을 변수
		List<FacilitiesValuation> fv;
		
		log.debug("[FacilitiesController] facility : {}", facility);
		
		if(facility == null) {
			log.debug("[FacilitiesController] findFacilitiesData : facility null");
			
			// parameter로 받은 시설 정보를 facilities 테이블에 insert
			int result = insertFacilities(facilities);
			
			// 해당 시설이 facilities테이블에 없었으므로 시설 평가도 없음 ==> null
			fv = null;
			
		} else { // 해당 시설의 정보가 이미 facilities 테이블에 있는 경우
			log.debug("[FacilitiesController] findFacilitiesData : facility not null");
			
			// 해당 시설의 리뷰 출력
			fv = printFacilitiesReivew(facilities);
			log.debug("[FacilitiesController] fv : {}", fv);
			
		}
		
		return fv;
	}
	
	/**
	 * 해당 시설의 리뷰를 가져오는 메소드
	 * @param facilities
	 * @return 시설 리뷰
	 */
	@ResponseBody
	@PostMapping("/printFacilitiesReivew")
	public List<FacilitiesValuation> printFacilitiesReivew(Facilities facilities) {
		log.debug("[FacilitiesController] printFacilitiesReivew");
		int facilitiesNumber = service.findFacilitiesNumber(facilities);
		log.debug("[FacilitiesController] printFacilitiesReivew - facilitiesNumber : {}", facilitiesNumber);
		
		// 시설 번호로 해당 시설의 리뷰를 가져오는 메소드
		List<FacilitiesValuation> fv = service.getFacilitiesReview(facilitiesNumber);
		log.debug("wow : {}", fv);
				
		return fv;
	}
	
	/**
	 * 시설의 평점을 찾는 메소드
	 * @param facilities
	 * @return 시설 평점
	 */
	@ResponseBody
	@PostMapping("/findFacilitiesStar")
	public double findFacilitiesStar(Facilities facilities) {
		log.debug("findFacilitiesStar : {}", facilities);
		
		int facilitesNumber = service.findFacilitiesNumber(facilities);
		log.debug("facilitesNumber : {}", facilitesNumber);
		
		double result = service.findFacilitiesStar(facilitesNumber);
		log.debug("result : {}", result);
		
		if(result > star) {
			return 0;
		}
		
		
		return result;
	}
	
	/**
	 * parameter로 받은 시설 정보를 facilities테이블에 넣는 메소드
	 * @param facilities
	 * @return 0 || 1
	 */
	public int insertFacilities(Facilities facilities) {
		
		if(facilities.getFacilitiesAddress().length() <= 0) {
			facilities.setFacilitiesAddress("없음");
		}
		if(facilities.getFacilitiesDetailAddress().length() <= 0) {
			facilities.setFacilitiesDetailAddress("없음");
		}
		if(facilities.getFacilitiesPhoneNumber().length() <= 0) {
			facilities.setFacilitiesPhoneNumber("없음");
		}
		
		int result = service.insertFacilities(facilities);
		
		return result;
	}
	
	/**
	 * 시설에 리뷰 작성 insert
	 * @param facilities
	 * @param fv
	 * @param user
	 * @return 0 || 1
	 */
	@ResponseBody
	@PostMapping("/insertFacilitiesReview")
	public int insertFacilitiesReview(Facilities facilities 
									 , FacilitiesValuation fv 
									 , @AuthenticationPrincipal UserDetails user
									 ) {
		log.debug("writeFacilitiesReview");
		log.debug("[FacilitiesController] "
				+ "writeFacilitiesReview - parameter : {} / {} / {}", 
				facilities, fv);
		
		// parameter로 전달받은 시설의 DB등록 번호를 찾는 메소드
		int facilitiesNumber = service.findFacilitiesNumber(facilities);
		log.debug("{}", facilitiesNumber);
		
		// 시설 번호 등록
		fv.setFacilitiesNumber(facilitiesNumber);
		
		// 회원 아이디 등록
		fv.setMemberId(user.getUsername()); 

		log.debug("ing : {}", fv);
		// FacilitiesValuation 테이블에 값을 넣는 메소드
		int result = service.insertFacilitiesReview(fv);
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("/deleteFacilitiesReview")
	public int deleteFacilitiesReview(int facilitiesEvaluationNumber
									,@AuthenticationPrincipal UserDetails user) {
		log.debug("[FacilitiesController] deleteFacilitiesReview - parameters1 : {}", facilitiesEvaluationNumber);
		log.debug("[FacilitiesController] deleteFacilitiesReview - parameters2 : {}", user);

		
		// parameter로 현재 계정의 아이디를 보내서 현재 계정의 role가져옴
		String checkRole = service.checkRole(user.getUsername());
		
		// role이 admin이면 해당 리뷰 삭제 
		int result = 0;
		if(checkRole.equals("ROLE_ADMIN")) {
			// 글 번호를 보내 삭제
			result = service.deleteFacilitiesReview(facilitiesEvaluationNumber);
			return result;
		}
		
		// role이 user면 해당 리뷰의 작성자 아이디를 현재 계정의 아이디와 비교 --> 일치시 삭제, 일치X 시 삭제 X
		if(checkRole.equals("ROLE_USER")) {
			// 해당 글 작성자의 아이디 조회
			String reviewWriter = service.checkReviewWrite(facilitiesEvaluationNumber);
			log.debug(reviewWriter);
			
			// 글 작성자와 삭제하려는 계정의 아이디가 같은 경우 삭제
			if(reviewWriter.equals(user.getUsername())) {
				result = service.deleteFacilitiesReview(facilitiesEvaluationNumber);
				return result;
			}
		}
		return result;
	}
}
