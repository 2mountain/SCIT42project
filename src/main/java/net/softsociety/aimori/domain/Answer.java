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
public class Answer {

	int answerNumber;
	int questionNumber;
	String memberId;
	String memberNickName;
	String memberRoleName;
	String answerContents;
	String answerInputDate;

}
