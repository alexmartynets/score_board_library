package model;

import java.time.LocalDateTime;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isEmpty() ||
                awayTeam == null || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team names must not be empty or null");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = LocalDateTime.now();
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setHomeScore(int homeScore) {
        if (homeScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        if (awayScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.awayScore = awayScore;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}