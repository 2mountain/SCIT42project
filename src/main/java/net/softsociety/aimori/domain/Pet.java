package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
	int petNumber;						// 강아지 고유 번호
	String memberId;					// 회원 ID
	String petName;						// 강아지 이름
	String petBreed;					// 강아지 종류
	String petGender;					// 강아지 성별
	int petBirthDay;					// 강아지 생년&월
	String petNeuter;					// 강아지 중성화 여부
	String petImage;					// 강아지 사진 파일 이름
	String petWeight;					// 강아지 체형
}
