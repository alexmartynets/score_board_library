import model.Match;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreBoardNegativeTest {

    @Test
    void testStartMatchWithEmptyTeamNames() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Match("", ""));
        assertEquals("Team names must not be empty or null", exception.getMessage());
    }

    @Test
    void testStartMatchWithNullTeamNames() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Match(null, null));
        assertEquals("Team names must not be empty or null", exception.getMessage());
    }

    @Test
    void testSetNegativeHomeScore() {
        Match match = new Match("Mexico", "Canada");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> match.setHomeScore(-1));
        assertEquals("Score cannot be negative", exception.getMessage());
    }

    @Test
    void testSetNegativeAwayScore() {
        Match match = new Match("Mexico", "Canada");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> match.setAwayScore(-1));
        assertEquals("Score cannot be negative", exception.getMessage());
    }
}