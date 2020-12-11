package worldofzuul.domain;

public class ScoreSystem {
    private static int score;
    private static int points;

    public ScoreSystem(int score){
    ScoreSystem.score = score;

    }
     public int getScore(){
        return score;
     }

    public void addToScore(int points){
        ScoreSystem.points = points;
        score = score + points;
    }
}
