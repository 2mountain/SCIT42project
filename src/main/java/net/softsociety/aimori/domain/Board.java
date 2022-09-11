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
public class Board {

	int boardNumber;    // 글 번호(BoardNumber_seq사용)
	String memberId;	// 회원 아이디
	String boardTitle;	// 글 제목
	String memberNickName;	// 작성자 닉네임
	String boardInputDate;	// 글작성일
	int boardHits;			// 글 조회수
	int boardLiked;			// 글 추천수
	int boardReport;		// 글 신고수
	String boardContents;	// 글 내용
	String boardCategory;	// 글 말머리
	String boardImageOriginal;	// 게시글 이미지 첨부파일
	String boardImageSaved;		// 이미지 첨부파일 서버에 저장된 이름
	
}
