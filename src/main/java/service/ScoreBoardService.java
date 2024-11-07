package service;

import exception.MatchNotFoundException;
import exception.MatchPresentException;
import model.Match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScoreBoardService {
    private final List<Match> tempStoreForMatches;

    public ScoreBoardService() {
        this.tempStoreForMatches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        if (isMatchExist(homeTeam, awayTeam)) {
            throw new MatchPresentException("Match already exists");
        }

        tempStoreForMatches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        getMatchByTeams(homeTeam, awayTeam).ifPresentOrElse(match -> {
            match.setHomeScore(homeScore);
            match.setAwayScore(awayScore);
        }, () -> {
            throw new MatchNotFoundException("You try to update ended or not started match");
        });
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        getMatchByTeams(homeTeam, awayTeam).ifPresent(tempStoreForMatches::remove);
    }

    public List<Match> getSummary() {
        return tempStoreForMatches.stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore)
                        .thenComparing(Match::getStartTime).reversed())
                .collect(Collectors.toList());
    }

    public boolean isMatchExist(String homeTeam, String awayTeam) {
        return getMatchByTeams(homeTeam, awayTeam).isPresent();
    }

    private Optional<Match> getMatchByTeams(String homeTeam, String awayTeam) {
        return tempStoreForMatches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst();
    }
}