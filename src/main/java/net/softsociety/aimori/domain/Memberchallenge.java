package net.softsociety.aimori.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Memberchallenge {
    int memberchallengenumber ;
    String memberId;
    int challengeNumber;
    String memberchallengename ;
    String memberchallengecontents; 
    String challengeOriginalFile;
    String challengeSavedFile;
}
