package service;

import exception.MatchNotFoundException;
import exception.MatchPresentException;
import model.Match;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ScoreBoardService {

    private final List<Match> tempStoreForMatches;
    private final Lock lock = new ReentrantLock();

    public ScoreBoardService() {
        this.tempStoreForMatches = new CopyOnWriteArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        lock.lock();
        try {
            if (isMatchExist(homeTeam, awayTeam)) {
                throw new MatchPresentException("Match already exists");
            }
            tempStoreForMatches.add(new Match(homeTeam, awayTeam));
        } finally {
            lock.unlock();
        }
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        validateScore(homeScore, awayScore);

        lock.lock();
        try {
            Optional<Match> matchOptional = getMatchByTeams(homeTeam, awayTeam);
            if (matchOptional.isPresent()) {
                Match match = matchOptional.get();
                match.setHomeScore(homeScore);
                match.setAwayScore(awayScore);
            } else {
                throw new MatchNotFoundException("Match not found or already ended");
            }
        } finally {
            lock.unlock();
        }
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        lock.lock();
        try {
            Optional<Match> matchOptional = getMatchByTeams(homeTeam, awayTeam);
            if (matchOptional.isPresent()) {
                tempStoreForMatches.remove(matchOptional.get());
            } else {
                throw new MatchNotFoundException("Match not found");
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Match> getSummary() {
        lock.lock();
        try {
            return tempStoreForMatches.stream()
                    .sorted(Comparator.comparingInt(Match::getTotalScore)
                            .thenComparing(Match::getStartTime).reversed())
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    public boolean isMatchExist(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        return getMatchByTeams(homeTeam, awayTeam).isPresent();
    }

    private Optional<Match> getMatchByTeams(String homeTeam, String awayTeam) {
        return tempStoreForMatches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isEmpty() ||
                awayTeam == null || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team names must not be empty or null");
        }
    }

    private void validateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}