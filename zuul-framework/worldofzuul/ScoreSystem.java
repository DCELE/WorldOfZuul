package worldofzuul;

public class ScoreSystem {
    private static int score;
    private static int points;

    public ScoreSystem(int score){
    this.score=score;

    }
     public int getScore(){
        return score;
     }

    public void addToScore(int points){
        this.points = points;
        score = score + points;
    }
}
