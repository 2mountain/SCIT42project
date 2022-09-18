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
public class BoardLiked {

	public BoardLiked(int boardNumber, String memberId) {
		this.boardNumber = boardNumber;
		this.memberId = memberId;
	}

	int boardLikedNumber;
	int boardNumber;
	String memberId;
	int boardLiked;

}
