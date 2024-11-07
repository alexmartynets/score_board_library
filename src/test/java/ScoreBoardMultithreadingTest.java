import model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ScoreBoardService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreBoardMultithreadingTest {

    private ScoreBoardService scoreBoardService;

    @BeforeEach
    public void setUp() {
        scoreBoardService = new ScoreBoardService();
    }

    @Test
    public void testConcurrentStartMatch() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 1; i <= numThreads; i++) {
            final int index = i;
            executorService.submit(() -> {
                String team1 = "HomeTeam" + index;
                String team2 = "AwayTeam" + index;
                scoreBoardService.startMatch(team1, team2);
            });
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        assertEquals(numThreads, scoreBoardService.getSummary().size());
    }

    @Test
    public void testConcurrentUpdateScore() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        String homeTeam = "HomeTeam";
        String awayTeam = "AwayTeam";
        scoreBoardService.startMatch(homeTeam, awayTeam);

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executorService.submit(() -> scoreBoardService.updateScore(homeTeam, awayTeam, index, index + 1));
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        Match match = scoreBoardService.getSummary().get(0);
        assertEquals(numThreads - 1, match.getHomeScore());
        assertEquals(numThreads, match.getAwayScore());
    }

    @Test
    public void testConcurrentFinishMatch() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        String homeTeam = "HomeTeam";
        String awayTeam = "AwayTeam";
        scoreBoardService.startMatch(homeTeam, awayTeam);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> scoreBoardService.finishMatch(homeTeam, awayTeam));
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        assertFalse(scoreBoardService.isMatchExist(homeTeam, awayTeam));
    }

    @Test
    public void testConcurrentOperations() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 1; i <= numThreads; i++) {
            final int index = i;
            executorService.submit(() -> {
                String team1 = "HomeTeam" + index;
                String team2 = "AwayTeam" + index;
                scoreBoardService.startMatch(team1, team2);
                scoreBoardService.updateScore(team1, team2, index, index + 1);
                scoreBoardService.finishMatch(team1, team2);
            });
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        assertEquals(0, scoreBoardService.getSummary().size());
    }
}