package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facilities {
	int facilitiesNumber; // 시설 번호,
    String facilitiesName; // 시설 이름,
    String facilitiesAddress; // 시설 도로명 주소
    String facilitiesDetailAddress; // 시설 지번 주소
    String facilitiesPhoneNumber; // 시설 전화번호
}
