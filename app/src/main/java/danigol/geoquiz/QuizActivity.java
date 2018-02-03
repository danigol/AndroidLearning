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

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private boolean mNextOnCorrect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called.");

        setContentView(R.layout.activity_quiz);

        // Create the player
        mPlayer = new Player(0, mQuestionBank.length, "Danielle");

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
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    }

    private void previousQuestion() {
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
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            if (mNextOnCorrect) {
                nextQuestion();
                updateQuestion();
            }
            updateScore(true);
        }
        else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this,
                messageResId, Toast.LENGTH_SHORT).show();
    }
}
