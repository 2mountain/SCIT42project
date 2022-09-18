package net.softsociety.aimori.service;

import net.softsociety.aimori.domain.Facilities;

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
}
