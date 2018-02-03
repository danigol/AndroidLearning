package danigol.geoquiz;

/**
 * Created by Danielle on 2/2/18.
 */

public class Player {
    private int mScore = 0;
    private int mQuestions = 0;
    private String mName = "Player 1";

    public Player(){
        mScore = 0;
        mQuestions = 0;
        mName = "Player 1";
    }
    public Player(int score, int questions, String name){
        mScore = score;
        mQuestions = questions;
        mName = name;
    }

    public int getScore(){
        return mScore;
    }

    public int getQuestions(){
        return mQuestions;
    }

    public String getName(){
        return mName;
    }

    /**
     * Returns true if score <= questions and therefore valid
     * @param score
     * @return
     */
    public boolean setScore(int score){
        // A valid score is <= the number of questions
        boolean validScore = false;

        if (score <= mQuestions){
            mScore = score;
            validScore = true;
        }

        return validScore;
    }

    public void setQuestions(int questions){
        mQuestions = questions;
    }

    public void setName(String name){
        mName = name;
    }

    public String generateScoreString(){
        return mName + " score: " + mScore + "/" + mQuestions;
    }
}
