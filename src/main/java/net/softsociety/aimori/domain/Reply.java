package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	int replynum; // PRIMARY KEY -- 댓글 번호
	int boardnum; // REFERENCES page_board(boardnum) ON DELETE CASCADE -- 본문 글 번호
	String memberid; // REFERENCES page_member(memberid) -- 작성자 ID
	String replytext; // NOT NULL -- 내용
	String inputdate; // DEFAULT SYSDATE -- 작성일
}
