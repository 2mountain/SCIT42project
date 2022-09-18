<<<<<<< HEAD
package net.softsociety.aimori.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관련 유틸
 * 업로드한 파일의 저장 & 서버에 저장된 파일 삭제 등의 기능 제공
 */
public class FileService {

	/**
	 * 업로드 된 파일을 지정된 경로에 저장하고, 저장된 파일명을 리턴
	 * @param mfile 업로드된 파일
	 * @param path 저장할 경로
	 * @return 저장된 파일명
	 */
	public static String saveFile(MultipartFile mfile, String uploadPath) {
		//업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			// 전달 받은 파일이 null이면 null 반환
			return null;
		}
		
		//사용자가 지정하고 parameter로 전달받은 저장 폴더가 없으면 생성
		File path = new File(uploadPath); // 경로만 생성
		if (!path.isDirectory()) { // directory가 존재하는지 여부 확인 ==> 없다면 
			path.mkdirs(); // mkdirs == make directories ==> 경로 생성
		}
		
		//원본 파일명
		String originalFilename = mfile.getOriginalFilename(); // 사용자가 저장해 parameter로 전달 받은 파일의 원본명
		
		//저장할 파일명을 오늘 날짜의 년월일로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // SimpleDateFormat 클래스 == 날짜를 원하는 모양으로 바꿈
		String savedFilename = sdf.format(new Date()); // 해당 날짜를 sdf에서 지정한 형식으로 저장
		
		//원본 파일의 확장자
		String ext;
		int lastIndex = originalFilename.lastIndexOf('.'); 
						// 확장자만 따로 저장 ( '.' 이라는 문자가 마지막에 나오는 위치 (int로) 확인 )
		//확장자가 없는 경우
		if (lastIndex == -1) {
			ext = ""; // 저장 X
		}
		//확장자가 있는 경우
		else {
			ext = "." + originalFilename.substring(lastIndex + 1); // .확장자 형식으로 String ext변수에 저장
									  // substring(index) == index 번째 값부터 그 이후의 값만 떼어냄
			// ex) abc.jpg 라는 이름의 파일 --> .이라는 문자의 마지막 위치 == 3(index이르므로 0부터 셈) 
			// --> 3 + 1 번째 위치부터의 값 앞에 . 붙여 저장 --> . + jpg --> .jpg (확장자)
		}

		//저장할 전체 경로와 파일명을 포함한 File 객체
		File serverFile = null;
		
		//같은 이름의 파일이 있는 경우의 처리
		while (true) {
			serverFile = new File(uploadPath + "/" + savedFilename + ext); // "사용자 지정 경로 + / + 파일명.확장자"로 저장
			//같은 이름의 파일이 없으면 나감.
			if ( !serverFile.isFile()) break;	// isFile 로 중복 이름 존재 여부 확인
			//같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임. ==> 이름 변경
			savedFilename = savedFilename + new Date().getTime(); // 오늘 날짜를 long형 타입의 긴 시간 정보로 생성해 파일명에 연결
		}		
		
		//파일 저장
		try {
			mfile.transferTo(serverFile); 
			// trasferTo(File file) == 파일을 지정 경로에 지정 이름으로 저장 == Ctrl C, Ctrl V
		} catch (Exception e) {
			savedFilename = null;
			e.printStackTrace();
		}
		
		return savedFilename + ext; // "저장된 이름 + 확장자 형식(.~~~)"으로 저장
	}
	
	/**
	 * 서버에 저장된 파일의 전체 경로를 전달받아, 해당 파일을 삭제
	 * @param fullpath 삭제할 파일의 경로
	 * @return 삭제 여부
	 */
	public static boolean deleteFile(String fullpath) {
		//파일 삭제 여부를 리턴할 변수
		boolean result = false;
		
		//전달된 전체 경로로 File객체 생성
		File delFile = new File(fullpath);
		
		//해당 파일이 존재하면 삭제
		if (delFile.isFile()) {
			delFile.delete(); // 지정 파일을 삭제하는 Method
			result = true;
		}
		
		return result;
	}
	
}
=======
package net.softsociety.aimori.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관련 유틸
 * 업로드한 파일의 저장 & 서버에 저장된 파일 삭제 등의 기능 제공
 */
public class FileService {

	/**
	 * 업로드 된 파일을 지정된 경로에 저장하고, 저장된 파일명을 리턴
	 * @param mfile 업로드된 파일
	 * @param path 저장할 경로
	 * @return 저장된 파일명
	 */
	public static String saveFile(MultipartFile mfile, String uploadPath) {
		//업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			// 전달 받은 파일이 null이면 null 반환
			return null;
		}
		
		//사용자가 지정하고 parameter로 전달받은 저장 폴더가 없으면 생성
		File path = new File(uploadPath); // 경로만 생성
		if (!path.isDirectory()) { // directory가 존재하는지 여부 확인 ==> 없다면 
			path.mkdirs(); // mkdirs == make directories ==> 경로 생성
		}
		
		//원본 파일명
		String originalFilename = mfile.getOriginalFilename(); // 사용자가 저장해 parameter로 전달 받은 파일의 원본명
		
		//저장할 파일명을 오늘 날짜의 년월일로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // SimpleDateFormat 클래스 == 날짜를 원하는 모양으로 바꿈
		String savedFilename = sdf.format(new Date()); // 해당 날짜를 sdf에서 지정한 형식으로 저장
		
		//원본 파일의 확장자
		String ext;
		int lastIndex = originalFilename.lastIndexOf('.'); 
						// 확장자만 따로 저장 ( '.' 이라는 문자가 마지막에 나오는 위치 (int로) 확인 )
		//확장자가 없는 경우
		if (lastIndex == -1) {
			ext = ""; // 저장 X
		}
		//확장자가 있는 경우
		else {
			ext = "." + originalFilename.substring(lastIndex + 1); // .확장자 형식으로 String ext변수에 저장
									  // substring(index) == index 번째 값부터 그 이후의 값만 떼어냄
			// ex) abc.jpg 라는 이름의 파일 --> .이라는 문자의 마지막 위치 == 3(index이르므로 0부터 셈) 
			// --> 3 + 1 번째 위치부터의 값 앞에 . 붙여 저장 --> . + jpg --> .jpg (확장자)
		}

		//저장할 전체 경로와 파일명을 포함한 File 객체
		File serverFile = null;
		
		//같은 이름의 파일이 있는 경우의 처리
		while (true) {
			serverFile = new File(uploadPath + "/" + savedFilename + ext); // "사용자 지정 경로 + / + 파일명.확장자"로 저장
			//같은 이름의 파일이 없으면 나감.
			if ( !serverFile.isFile()) break;	// isFile 로 중복 이름 존재 여부 확인
			//같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임. ==> 이름 변경
			savedFilename = savedFilename + new Date().getTime(); // 오늘 날짜를 long형 타입의 긴 시간 정보로 생성해 파일명에 연결
		}		
		
		//파일 저장
		try {
			mfile.transferTo(serverFile); 
			// trasferTo(File file) == 파일을 지정 경로에 지정 이름으로 저장 == Ctrl C, Ctrl V
		} catch (Exception e) {
			savedFilename = null;
			e.printStackTrace();
		}
		
		return savedFilename + ext; // "저장된 이름 + 확장자 형식(.~~~)"으로 저장
	}
	
	/**
	 * 서버에 저장된 파일의 전체 경로를 전달받아, 해당 파일을 삭제
	 * @param fullpath 삭제할 파일의 경로
	 * @return 삭제 여부
	 */
	public static boolean deleteFile(String fullpath) {
		//파일 삭제 여부를 리턴할 변수
		boolean result = false;
		
		//전달된 전체 경로로 File객체 생성
		File delFile = new File(fullpath);
		
		//해당 파일이 존재하면 삭제
		if (delFile.isFile()) {
			delFile.delete(); // 지정 파일을 삭제하는 Method
			result = true;
		}
		
		return result;
	}
	
}
>>>>>>> d4e06de82319739db0968b3d300578b7658f1f5a
