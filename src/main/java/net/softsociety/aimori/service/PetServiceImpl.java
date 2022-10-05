package net.softsociety.aimori.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.PetDAO;
import net.softsociety.aimori.domain.Pet;

@Service
@Slf4j
public class PetServiceImpl implements PetService {
	
	@Autowired
	PetDAO petDAO;
	
	// 펫 정보 입력
	@Override
	public int petInfoInput(Pet pet) {
		
		int result = petDAO.petInfoInput(pet);
		
		return result;
	}

	@Override
	public Pet petInfoUpdate(Pet pet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pet petInfoRead(int petNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pet selectPet(String memberId) {
		Pet pet = petDAO.selectPet(memberId);
		return pet;
	}
}
