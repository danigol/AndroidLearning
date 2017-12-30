package danigol.geoquiz;

/**
 * Created by daniellegolinsky on 12/6/17.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean getAnswerTrue(){
        return mAnswerTrue;
    }

    public void setTextResId(int textResId){
        mTextResId = textResId;
    }

    public void setGetAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }
}
