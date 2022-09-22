package net.softsociety.aimori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.AdministratorDAO;
import net.softsociety.aimori.domain.Member;

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

}
