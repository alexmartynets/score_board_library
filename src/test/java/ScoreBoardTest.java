import model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ScoreBoardService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreBoardTest {
    private ScoreBoardService scoreboard;

    @BeforeEach
    public void setup() {
        scoreboard = new ScoreBoardService();
    }

    @Test
    void testStartMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        List<Match> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());
        assertEquals("Mexico 0 - Canada 0", summary.getFirst().toString());
    }

    @Test
    public void testUpdateScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 3, 2);
        List<Match> summary = scoreboard.getSummary();
        assertEquals("Mexico 3 - Canada 2", summary.getFirst().toString());
    }

    @Test
    public void testFinishMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.finishMatch("Mexico", "Canada");
        List<Match> summary = scoreboard.getSummary();
        assertEquals(0, summary.size());
    }

    @Test
    public void testGetSummary() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.startMatch("Germany", "France");
        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.startMatch("Argentina", "Australia");

        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay 6 - Italy 6", summary.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", summary.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", summary.get(3).toString());
        assertEquals("Germany 2 - France 2", summary.get(4).toString());
    }
}