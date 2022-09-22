package net.softsociety.aimori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softsociety.aimori.dao.AdministratorDAO;
import net.softsociety.aimori.domain.Member;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	AdministratorDAO dao;
	
	@Override
	public List<Member> getMemberList() {
		List<Member> list = dao.getMemberList();
		return list;
	}

}
