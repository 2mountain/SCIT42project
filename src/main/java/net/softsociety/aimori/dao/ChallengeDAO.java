package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Challenge;

@Mapper	
public interface ChallengeDAO {

	int countchallenge(HashMap<String, String> map);

	int countconfience(HashMap<String, String> map);

	ArrayList<Challenge> confienceList(HashMap<String, String> map, RowBounds rb);

	ArrayList<Challenge> challengeList(HashMap<String, String> map, RowBounds rb);

}
