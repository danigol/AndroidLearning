package danigol.geoquiz;

/**
 * Created by daniellegolinsky on 12/6/17.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAlreadySeenOrGuessed = false;

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

    /**
     * Since we're keeping score, we can't allow someone to go back
     *      and answer questions they've already seen repeatedly for
     *      more points. Skipping means disabling your ability to get
     *      points for a question.
     * @param seenOrGuessed
     */
    public void setAlreadySeenOrGuessed(boolean seenOrGuessed){
        mAlreadySeenOrGuessed = seenOrGuessed;
    }

    public boolean alreadySeenOrGuessed(){
        return mAlreadySeenOrGuessed;
    }
}
