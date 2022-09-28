package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrylist {

	int entrylistNumber;
	String memberId;
	int challengeNumber;
	int challengeSuccess;
	int getPoint;
	String getPointDate;

}
