package danigol.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private TextView mScoreView;
    private CheckBox mNextQuestionOnCorrectCheckBox;

    private Player mPlayer;

    private Question[] mQuestionBank = QuestionBank.getInstance().getQuestionBook();

    private int mCurrentIndex = 0;
    private boolean mNextOnCorrect = true;

    // Keys for saving state
    private static final String KEY_INDEX = "index"; // Question index
    private static final String KEY_PLAYER = "player_name";
    private static final String KEY_SCORE = "player_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called.");

        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null){
            // Recover the player name, score, and the current index
            String playerName = savedInstanceState.getString(KEY_PLAYER, "Player 1");
            int playerScore = savedInstanceState.getInt(KEY_SCORE, 0);
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            // Create the player
            mPlayer = new Player(playerScore, mQuestionBank.length, playerName);
        }
        else {
            // Create the player
            mPlayer = new Player(0, mQuestionBank.length, "Danielle");
        }

        // Initialize text view before updating content
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(v -> {
            Toast.makeText(QuizActivity.this,
                    R.string.skip_question, Toast.LENGTH_SHORT).show();
            nextQuestion();
            updateQuestion();
        });

        // Update the score
        mScoreView = (TextView) findViewById(R.id.player_score);
        updateScore(false);

        // Display first question
        updateQuestion();

        // Set up buttons and options
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> checkAnswer(true));

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> checkAnswer(false));

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> {
            nextQuestion();
            updateQuestion();
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(v ->  {
            previousQuestion();
            updateQuestion();
        });

        mNextQuestionOnCorrectCheckBox =
                (CheckBox) findViewById(R.id.checkbox_next_question_on_correct);
        mNextQuestionOnCorrectCheckBox.setOnClickListener(v -> mNextOnCorrect = !mNextOnCorrect);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putString(KEY_PLAYER, mPlayer.getName());
        savedInstanceState.putInt(KEY_SCORE, mPlayer.getScore());
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called.");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called.");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called.");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called.");
    }

    private void nextQuestion() {
        // Skipping a question marks it as "seen"
        mQuestionBank[mCurrentIndex].setAlreadySeenOrGuessed(true);
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    }

    private void previousQuestion() {
        mQuestionBank[mCurrentIndex].setAlreadySeenOrGuessed(true);
        mCurrentIndex--;
        if (mCurrentIndex < 0) {
            mCurrentIndex = (mQuestionBank.length - 1);
        }
    }

    private void updateScore(boolean wasCorrect){
        if (wasCorrect){
            mPlayer.setScore(mPlayer.getScore() + 1);
        }
        mScoreView.setText(mPlayer.generateScoreString());
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (mQuestionBank[mCurrentIndex].alreadySeenOrGuessed()) {
            Toast.makeText(QuizActivity.this,
                    R.string.seen_question_warn, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {

            // Only advance score if this isn't the first time
            //      seeing this question.
            if (!mQuestionBank[mCurrentIndex].alreadySeenOrGuessed()) {
                updateScore(true);
                messageResId = R.string.correct_toast;
            }
            else {
                messageResId = R.string.correct_seen_toast;
            }

            // Set that we've displayed this question to the user
            mQuestionBank[mCurrentIndex].setAlreadySeenOrGuessed(true);

            // Go to the next question if they have that checked off
            if (mNextOnCorrect) {
                nextQuestion();
                updateQuestion();
            }
        }
        else {
            messageResId = R.string.incorrect_toast;
            // Set that the player has already guessed this question
            mQuestionBank[mCurrentIndex].setAlreadySeenOrGuessed(true);
        }

        Toast.makeText(QuizActivity.this,
                messageResId, Toast.LENGTH_SHORT).show();
    }
}
