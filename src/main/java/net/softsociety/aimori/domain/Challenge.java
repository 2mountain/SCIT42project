package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
 int challengeNumber;
 String challengeName;
 String challengeStartDate;
 String challengEnddate;
 String challengRegistday;
 String challengeContents;
 String challengeType;
 
}