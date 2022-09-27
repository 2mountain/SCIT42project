package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedBoard {
	String memberId;
	int boardNumber;
	String boardTitle;
	String boardInputDate;
	String reportCategory;
	int reportCount;
}
