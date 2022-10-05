package net.softsociety.aimori.service;

import net.softsociety.aimori.domain.Pet;

public interface PetService {
	Object selectPet = null;

	// 펫 정보 입력
	public int petInfoInput(Pet pet);
	
	// 펫 정보 수정
	public Pet petInfoUpdate(Pet pet);
	
	// 펫 정보 읽기
	public Pet petInfoRead(int petNumber);

	// 펫 정보 메인에 뿌리기
	public Pet selectPet(String memberId);
}
