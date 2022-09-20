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
public class Reply {
	int replyNumber;
	int boardNumber;
	String memberId;
	String memberNickName;
	String replyContents;
	String replyInputDate;
}
