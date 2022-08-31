package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	String boardnum; // PRIMARY KEY, 글번호
	String memberid; // fk REFERENCES page_member(memberid), 작성자 ID 
	String title; // NOT NULL,  제목
	String contents; // NOT NULL, 글 내용
	String input; // DATE DEFAULT SYSDATE, 작성일
	int hits; // DEFAULT 0, 조회수
	String originalfile; // 첨부파일 1개의 원래 이름
	String savedfile; // 첨부파일이 서버에 저장된 이름   
}
