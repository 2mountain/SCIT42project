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
public class Member {

    String memberId;		// 회원 아이디
    String memberPassword;	// 회원 비밀번호
    String memberNickName;	// 회원 닉네임 
    String memberAddress;	// 회원 주소 
    String memberEmail;		// 회원 이메일
    String memberBirtyDay;	// 회원 생년월일
    int memberPoint;		// 회원 포인트
    String memberRoleName;	// 회원 역할
    int memberEnabled;		// 회원 차단 여부
    String memberJoinDate;	// 회원 가입날짜
    String memberImageOriginalFile; // 대표 이미지의 원래 이름
    String memberImageSavedFile;

}
