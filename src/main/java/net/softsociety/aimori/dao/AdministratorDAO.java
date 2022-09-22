package net.softsociety.aimori.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.aimori.domain.Member;

@Mapper
public interface AdministratorDAO {

	public List<Member> getMemberList();
}
