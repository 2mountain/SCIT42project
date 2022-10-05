package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedBoard {
	
	// temp
	public ReportedBoard(int boardNumber, String memberId) {
		this.boardNumber = boardNumber;
		this.memberId = memberId;
	}
	
	String memberId;			// 작성자 아이디
	int boardNumber;			// 게시글 번호
	int questionNumber;			// 질문 번호
	String boardTitle;			// 게식글 제목
	String questionTitle;		// 질문 제목
	String boardInputDate;		// 게시글 작성일
	String questionInputDate;	// 질문 작성일
	String reportCategory;		// 신고사유
	String boardCategory;		// 게시글 말머리
	String questionCategory;	// 질문게시판 말머리
	int reportBCount;			// 게시글 신고 수
	int reportQCount;			// 질문 신고 수
}
