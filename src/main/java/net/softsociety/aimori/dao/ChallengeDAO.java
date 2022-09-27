package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Challenge;
import net.softsociety.aimori.domain.Entrylist;

@Mapper	
public interface ChallengeDAO {

	int countChallenge(HashMap<String, String> map);

	int countContest(HashMap<String, String> map);

	ArrayList<Challenge> contestList(HashMap<String, String> map, RowBounds rb);

	ArrayList<Challenge> challengeList(HashMap<String, String> map, RowBounds rb);

	Challenge readChallenge(int challengeNumber);

	ArrayList<Entrylist> getEntrylist(int challengeNumber);

	

}
