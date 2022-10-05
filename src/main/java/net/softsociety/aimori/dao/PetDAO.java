package net.softsociety.aimori.dao;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.aimori.domain.Pet;

@Mapper
public interface PetDAO {
	// 펫 정보 입력
	public int petInfoInput(Pet pet);
	
	// 펫 정보 수정
	public Pet petInfoUpdate(Pet pet);
	
	// 펫 정보 읽기
	public Pet petInfoRead(int petNumber);

	public Pet selectPet(String memberId);
}
