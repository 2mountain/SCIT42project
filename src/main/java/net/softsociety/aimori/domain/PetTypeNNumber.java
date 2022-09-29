package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetTypeNNumber {
	String petBreed;	// 강아지 종류
	int petBreedCount;  // 해당 견종 수
}
