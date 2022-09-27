package net.softsociety.aimori.service;

import net.softsociety.aimori.domain.Pet;

public interface PetService {
	// 펫 정보 입력
	public int petInfoInput(Pet pet);
	
	// 펫 정보 수정
	public Pet petInfoUpdate(Pet pet);
	
	// 펫 정보 읽기
	public Pet petInfoRead(int petNumber);
}
