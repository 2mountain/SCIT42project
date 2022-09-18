package net.softsociety.aimori.dao;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.aimori.domain.Facilities;

@Mapper
public interface FacilitiesDAO {
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

}
