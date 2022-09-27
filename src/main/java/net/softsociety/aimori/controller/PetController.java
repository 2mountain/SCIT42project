package net.softsociety.aimori.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Pet;
import net.softsociety.aimori.service.PetService;
import net.softsociety.aimori.util.FileService;

@Slf4j
@RequestMapping("mypage")
@Controller
public class PetController{
	
	@Autowired
	PetService Pservice;
	
	// 첨부파일 저장할 경로
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	// 펫 정보 입력 페이지로 이동
	@GetMapping("/petInfoInput")
	public String petInfoInput() {
		
		return "/mypageView/petInfoInput";
	}
	
	// 펫 정보 입력
	@PostMapping("/petInfoInput")
	public String petInfoInput(Pet pet, @AuthenticationPrincipal UserDetails user, MultipartFile upload) {
		
		pet.setMemberId(user.getUsername());
		
		String petImage = FileService.saveFile(upload, uploadPath);
		pet.setPetImage(petImage);
		
		Pservice.petInfoInput(pet);
		
		return "/mypageView/petInfoInput";
		
	}
	
	// 펫 정보 읽기
	   @GetMapping("/petInfoRead")
	   public String petInfoRead(Model model, @RequestParam(name="petNumber", defaultValue = "0")int petNumber) {
	      // 펫 정보
	      Pet pet = Pservice.petInfoRead(petNumber);
	      
	      model.addAttribute("Pet", pet);
	      
	      return "/redirect:/mypageView/petInfoUpdate";   
	   }
	
	// 펫 정보 수정
	   @GetMapping("/petInfoUpdate")
	   public String petInfoUpdate(int petNumber, Model model) {
	      
	      Pet pet = Pservice.petInfoRead(petNumber);
	      
	      model.addAttribute("Pet", pet);
	      
	      return "/redirect:/mypageView/petInfoUpdate";
	   }
	   
	   @PostMapping("/petInfoUpdate")
	   public String petInfoUpdate(Pet pet, @AuthenticationPrincipal UserDetails user , MultipartFile upload) {
	      
	      pet.setMemberId(user.getUsername());
	      
	      String petImage = FileService.saveFile(upload, uploadPath);
	      pet.setPetImage(petImage);
	       	      
	      Pservice.petInfoUpdate(pet);
	     
	      return "redirect:/mypageView/petInfoRead?petNumber=" + pet.getPetNumber();
	   }
	   
	   @GetMapping("download")
	   public String photoUpload(int petNumber, Model model, HttpServletResponse response ) {
	         
	      // 애완동물 정보 조회
	      Pet pet = Pservice.petInfoRead(petNumber);
	      
	      //원래의 파일명
	      String petImage = new String(pet.getPetImage());
	      try {
	         response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(petImage, "UTF-8"));
	      } catch (UnsupportedEncodingException e) {
	         e.printStackTrace();
	      }
	      
	      // 저장된 파일경로
	      String fullPath = uploadPath + "/" + pet.getPetImage();
	      
	      //서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
	      FileInputStream filein = null;
	      ServletOutputStream fileout = null;
	      
	      try {
	         filein = new FileInputStream(fullPath);
	         fileout = response.getOutputStream();
	         //Spring의 파일 관련 유틸 이용하여 출력
	         FileCopyUtils.copy(filein, fileout);
	         
	         filein.close();
	         fileout.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return "redirect:/";
	   }

}
