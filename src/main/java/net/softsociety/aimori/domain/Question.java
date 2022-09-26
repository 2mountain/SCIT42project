package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 정보
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

	int questionNumber;
	String memberId;
	String questionTitle;
	String memberNickName;
	String questionInputDate;
	int questionHits;
	int questionReport;
	String questionContents;
	String questionCategory;
	String questionImageOriginal;
	String questionImageSaved;
	int answerCount;
	int answeredOrNot;
	int answerAccepted;

}
