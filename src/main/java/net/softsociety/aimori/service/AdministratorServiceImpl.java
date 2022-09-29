package net.softsociety.aimori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.AdministratorDAO;
import net.softsociety.aimori.domain.DateJoinNumber;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.PetTypeNNumber;
import net.softsociety.aimori.domain.ReportedBoard;

@Slf4j
@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	AdministratorDAO dao;
	
	@Override
	public List<Member> getMemberList() {
		List<Member> list = dao.getMemberList();
		return list;
	}

	@Override
	public int changeBlock(Member member) {
		int result = dao.changeBlock(member);
		log.debug("[AdministratorServiceImpl] changeBlock : {}", result);
		return result;
	}

	@Override
	public int changeRole(Member member) {
		int result = dao.changeRole(member);
		log.debug("[AdministratorServiceImpl] changeRole : {}", result);
		return result;
	}

	@Override
	public List<ReportedBoard> getReportedBoard() {
		List<ReportedBoard> list = dao.getReportedBoard();
		return list;
	}

	@Override
	public List<DateJoinNumber> getJoinNumber() {
		List<DateJoinNumber> list = dao.getJoinNumber();
		return list;
	}

	@Override
	public List<PetTypeNNumber> getDogType() {
		List<PetTypeNNumber> list = dao.getDogType();
		return list;
	}

}
