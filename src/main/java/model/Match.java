package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam) {
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
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;
        return homeScore == match.homeScore &&
                awayScore == match.awayScore &&
                homeTeam.equals(match.homeTeam) &&
                awayTeam.equals(match.awayTeam) &&
                startTime.equals(match.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, startTime);
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}