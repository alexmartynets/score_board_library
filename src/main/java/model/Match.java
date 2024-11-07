package model;

import java.time.LocalDateTime;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Match(String homeTeam, String awayTeam, LocalDateTime startTime, LocalDateTime endTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}