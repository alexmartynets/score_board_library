# ScoreBoardService Documentation

## Overview

The `ScoreBoardService` class provides functionality to manage and track scores for matches between teams. It allows you to start new matches, update scores, finish matches, and retrieve a summary of ongoing matches.

## Getting Started

### Adding a New Match

To start a new match between two teams, use the `startMatch` method. This method ensures that match does not already exist before adding it to the list.

```java
ScoreBoardService scoreBoardService = new ScoreBoardService();
scoreBoardService.startMatch("Team A", "Team B");
```

### Updating Scores

To update the scores of an ongoing match, use the `updateScore` method. This method ensures that the match exists and updates both the home and away scores.

```java
scoreBoardService.updateScore("Team A", "Team B", 10, 5);
```

### Finishing a Match

To finish an ongoing match and remove it from the list, use the `finishMatch` method.

```java
scoreBoardService.finishMatch("Team A", "Team B");
```

### Getting a Summary of Matches

To get a sorted list of all ongoing matches (sorted by total score and start time), use the `getSummary` method.

```java
List<Match> summary = scoreBoardService.getSummary();
for (Match match : summary) {
    System.out.println(match);
}
```

### Checking for Existing Matches

To check if a match between two teams already exists, use the `isMatchExist` method.

```java
boolean matchExists = scoreBoardService.isMatchExist("Team A", "Team B");
System.out.println("Match exists: " + matchExists);
```

## Class and Method Details

### `ScoreBoardService`

This class is the main service for managing matches.

- **Methods**:
    - `public ScoreBoardService()`: Constructor that initializes the match list.
    - `public void startMatch(String homeTeam, String awayTeam)`: Starts a new match.
    - `public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore)`: Updates the scores of an existing match.
    - `public void finishMatch(String homeTeam, String awayTeam)`: Finishes an existing match.
    - `public List<Match> getSummary()`: Returns a sorted summary of all ongoing matches.
    - `public boolean isMatchExist(String homeTeam, String awayTeam)`: Checks if a match exists.

### `Match`

This class represents a match between two teams and includes their scores and start time.

- **Constructor**:
    - `public Match(String homeTeam, String awayTeam)`: Initializes a match with the given team names and sets the initial scores to zero.

- **Methods**:
    - `public String getHomeTeam()`: Returns the name of the home team.
    - `public String getAwayTeam()`: Returns the name of the away team.
    - `public int getHomeScore()`: Returns the score of the home team.
    - `public void setHomeScore(int homeScore)`: Sets the score of the home team.
    - `public int getAwayScore()`: Returns the score of the away team.
    - `public void setAwayScore(int awayScore)`: Sets the score of the away team.
    - `public LocalDateTime getStartTime()`: Returns the start time of the match.
    - `public int getTotalScore()`: Returns the total score of both teams.
    - `public String toString()`: Returns a string representation of the match.

### Exceptions

- `MatchNotFoundException`: Thrown when attempting to update or finish a non-existing match.
- `MatchPresentException`: Thrown when attempting to start an already existing match.

### Example Usage

```java
public class Main {
    public static void main(String[] args) {
        ScoreBoardService scoreBoardService = new ScoreBoardService();

        // Start a new match
        scoreBoardService.startMatch("Team A", "Team B");

        // Update the scores of the match
        scoreBoardService.updateScore("Team A", "Team B", 10, 8);

        // Get a summary of all ongoing matches
        List<Match> matches = scoreBoardService.getSummary();
        for (Match match : matches) {
            System.out.println(match);
        }

        // Finish the match
        scoreBoardService.finishMatch("Team A", "Team B");

        // Check if a match exists
        boolean exists = scoreBoardService.isMatchExist("Team A", "Team B");
        System.out.println("Match exists: " + exists);
    }
}
```

This documentation provides a comprehensive guide to using the `ScoreBoardService` to manage matches and their scores effectively.