package danigol.geoquiz;

/**
 * Created by Danielle on 2/3/18.
 * This class will store the questions in a singleton, separate from the quiz activity
 */

public class QuestionBank {

    private static QuestionBank instance;

    private Question[] mQuestionBook = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private QuestionBank() {
    }

    public static QuestionBank getInstance() {
        if (instance == null){
            instance = new QuestionBank();
        }
        return instance;
    }

    public Question[] getQuestionBook(){
        return mQuestionBook;
    }

    public void reset(){
        for (Question q : mQuestionBook){
            q.setAlreadySeenOrGuessed(false);
        }
    }
}
