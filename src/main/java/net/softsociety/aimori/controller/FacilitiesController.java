package net.softsociety.aimori.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	FacilitiesService service;
	
	@GetMapping({"", "/"})
	public String facilities(Model model) {
		
		//이부분에다가 값을 바꿔주면 처음 띄울 때 값이 바뀐다!
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
	public FacilitiesValuation findFacilitiesData(Facilities facilities) {
		log.debug("★ findFacilitiesData");
		log.debug("[FacilitiesController] findFacilitiesData - parameter : {}", facilities);

		// parameter와 일치하는 시설이 있는지 확인하는 메소드
		Facilities facility = service.findFacilities(facilities);
		log.debug("[FacilitiesController] findFacilitiesData - findFacilitiesData - result : {}", facility);
		
		
		// 시설의 정보가 이미 DB에 있는 경우, 리뷰를 불러와 담을 변수
		FacilitiesValuation fv;
		
		if(facility == null) {
			log.debug("[FacilitiesController] findFacilitiesData : facility null");
			
			// parameter로 받은 시설 정보를 facilities 테이블에 insert
			int result = insertFacilities(facilities);
			
			// 해당 시설이 facilities테이블에 없었으므로 시설 평가도 없음 ==> null
			fv = null;
			
		} else { // 해당 시설의 정보가 이미 facilities 테이블에 있는 경우
			log.debug("[FacilitiesController] findFacilitiesData : facility not null");
			
			// 여기 수정 중
			// ★★★★★★★★★★★★★★★ DB에서 받은 데이터를 fv(시설 리뷰)에 넣는 것으로 수정 ★★★★★★★★★★★★★★★★★★★★
			fv = new FacilitiesValuation(0, 0, null, null, 0, null);
			
		}
		
		return fv;
	}
	
	/**
	 * parameter로 받은 시설 정보를 facilities테이블에 넣는 메소드
	 * @param facilities
	 * @return 0 || 1
	 */
	public int insertFacilities(Facilities facilities) {
		
		int result = service.insertFacilities(facilities);
		
		return result;
	}
}
