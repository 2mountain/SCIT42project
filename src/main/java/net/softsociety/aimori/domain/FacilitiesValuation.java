package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesValuation {
	int 	facilitiesEvaluationNumber; // 시설 평가 번호
	int 	facilitiesNumber; 			// 시설 번호
	String 	memberId; 					// 리뷰 작성 회원 아이디
	String 	facilitiesReview; 			// 시설 리뷰 내용
	double 	facilitiesStar; 			// 시설 평가(별점)
	String 	facilitiesReviewDate;	 	// 시설 리뷰 작성 일자
	}
