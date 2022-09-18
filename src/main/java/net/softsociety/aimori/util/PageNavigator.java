<<<<<<< HEAD
package net.softsociety.aimori.util;

import lombok.Data;

/**
 * 게시판 페이징 처리 클래스
 */
@Data
public class PageNavigator {
	//페이지 관련 정보 
	private int countPerPage;		//페이지당 글목록 수
	private int pagePerGroup;		//그룹당 페이지 수 < 1 2 3 4 5 > ==> 한 그룹 당 페이지 5개
	private int currentPage;		//현재 페이지 (최근 글이 1부터 시작)
	private int totalRecordsCount;	//전체 글 수
	private int totalPageCount;		//전체 페이지 수
	private int currentGroup;		//현재 그룹 (최근 그룹이 0부터 시작) 
										// 페이지 선택  
										// --> 0번 그룹 <  1 2 3 4 5  >  
										//     1번 그룹 <  6 7 8 9 10  > ...
	private int startPageGroup;		//현재 그룹의 첫 페이지
	private int endPageGroup;		//현재 그룹의 마지막 페이지
	private int startRecord;		//전체 레코드(목록) 중 현재 페이지 첫 글의 위치 (0부터 시작)
	
	/*
	 * 생성자
	 */
	public PageNavigator(int pagePerGroup, int countPerPage, int currentPage, 
			int totalRecordsCount) {
		//그룹당 페이지 수, 페이지당 글 수, 현재 페이지, 전체 글 수를 전달받음
		this.countPerPage = countPerPage;
		this.pagePerGroup = pagePerGroup;
		this.totalRecordsCount = totalRecordsCount;
		
		//전체 페이지 수
		totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;
							// "전체 글 목록의 수에 + 페이지 당 글 목록 수" 하지 않으면
							// 글이 10개 미만일 때 "페이지 당 글 목록 수"로 나누었을 때 몫이 0
							// 전체 글 목록의 수가 나누어 떨어지는 수일 때 -1 이 없으면 페이지 수가 1개 더 나옴
							// if 글 목록이 100개 일 때 -1 이 없으면
							//    (100+10)/10 == 11페이지 but 실제로는 10페이지 필요
		//전달된 현재 페이지가 1보다 작으면 현재페이지를 1페이지로 지정
		if (currentPage < 1)	currentPage = 1;
		//전달된 현재 페이지가 마지막 페이지보다 크면 현재페이지를 마지막 페이지로 지정
		if (currentPage > totalPageCount)	currentPage = totalPageCount;
		
		this.currentPage = currentPage;

		//현재 그룹
		currentGroup = (currentPage - 1) / pagePerGroup;
		
		//현재 그룹의 첫 페이지
		startPageGroup = currentGroup * pagePerGroup + 1;
		//현재 그룹의 첫 페이지가 1보다 작으면 1로 처리
		startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
		//현재 그룹의 마지막 페이지
		endPageGroup = startPageGroup + pagePerGroup - 1;
		//현재 그룹의 마지막 페이지가 전체 페이지 수보다 작으면 전체페이지 수를 마지막으로.
		endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;

		//전체 레코드 중 현재 페이지 첫 글이 있는 위치
		startRecord = (currentPage - 1) * countPerPage;		
		// if 현재 페이지가 4페이지, 한 페이지 당 글 수 = 10 --> 그룹이 0번부터 시작하므로 3번 그룹에 위치함 
		//     --> ( 4(페이지) - 1 ) * 페이지당 글 수(10) == 30 
		//         ==> 전체 레코드 중 현재 페이지 첫 글의 위치 나옴
	}
}
=======
package net.softsociety.aimori.util;

import lombok.Data;

/**
 * 게시판 페이징 처리 클래스
 */
@Data
public class PageNavigator {
	//페이지 관련 정보 
	private int countPerPage;		//페이지당 글목록 수
	private int pagePerGroup;		//그룹당 페이지 수 < 1 2 3 4 5 > ==> 한 그룹 당 페이지 5개
	private int currentPage;		//현재 페이지 (최근 글이 1부터 시작)
	private int totalRecordsCount;	//전체 글 수
	private int totalPageCount;		//전체 페이지 수
	private int currentGroup;		//현재 그룹 (최근 그룹이 0부터 시작) 
										// 페이지 선택  
										// --> 0번 그룹 <  1 2 3 4 5  >  
										//     1번 그룹 <  6 7 8 9 10  > ...
	private int startPageGroup;		//현재 그룹의 첫 페이지
	private int endPageGroup;		//현재 그룹의 마지막 페이지
	private int startRecord;		//전체 레코드(목록) 중 현재 페이지 첫 글의 위치 (0부터 시작)
	
	/*
	 * 생성자
	 */
	public PageNavigator(int pagePerGroup, int countPerPage, int currentPage, 
			int totalRecordsCount) {
		//그룹당 페이지 수, 페이지당 글 수, 현재 페이지, 전체 글 수를 전달받음
		this.countPerPage = countPerPage;
		this.pagePerGroup = pagePerGroup;
		this.totalRecordsCount = totalRecordsCount;
		
		//전체 페이지 수
		totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;
							// "전체 글 목록의 수에 + 페이지 당 글 목록 수" 하지 않으면
							// 글이 10개 미만일 때 "페이지 당 글 목록 수"로 나누었을 때 몫이 0
							// 전체 글 목록의 수가 나누어 떨어지는 수일 때 -1 이 없으면 페이지 수가 1개 더 나옴
							// if 글 목록이 100개 일 때 -1 이 없으면
							//    (100+10)/10 == 11페이지 but 실제로는 10페이지 필요
		//전달된 현재 페이지가 1보다 작으면 현재페이지를 1페이지로 지정
		if (currentPage < 1)	currentPage = 1;
		//전달된 현재 페이지가 마지막 페이지보다 크면 현재페이지를 마지막 페이지로 지정
		if (currentPage > totalPageCount)	currentPage = totalPageCount;
		
		this.currentPage = currentPage;

		//현재 그룹
		currentGroup = (currentPage - 1) / pagePerGroup;
		
		//현재 그룹의 첫 페이지
		startPageGroup = currentGroup * pagePerGroup + 1;
		//현재 그룹의 첫 페이지가 1보다 작으면 1로 처리
		startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
		//현재 그룹의 마지막 페이지
		endPageGroup = startPageGroup + pagePerGroup - 1;
		//현재 그룹의 마지막 페이지가 전체 페이지 수보다 작으면 전체페이지 수를 마지막으로.
		endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;

		//전체 레코드 중 현재 페이지 첫 글이 있는 위치
		startRecord = (currentPage - 1) * countPerPage;		
		// if 현재 페이지가 4페이지, 한 페이지 당 글 수 = 10 --> 그룹이 0번부터 시작하므로 3번 그룹에 위치함 
		//     --> ( 4(페이지) - 1 ) * 페이지당 글 수(10) == 30 
		//         ==> 전체 레코드 중 현재 페이지 첫 글의 위치 나옴
	}
}
>>>>>>> d4e06de82319739db0968b3d300578b7658f1f5a
