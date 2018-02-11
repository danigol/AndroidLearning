package danigol.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "danigol.geoquiz.answer_is_true";

    private static final String EXTRA_ANSWER_SHOWN =
            "danigol.geoquiz.answer_shown";

    private static final String EXTRA_CHEATS_LEFT =
            "danigol.geoquiz.cheats_left";

    private static final String KEY_PLAYER_CHEATED = "player_cheated";
    private static final String KEY_CHEATS_LEFT = "cheats_left";

    private boolean mAnswerIsTrue;
    private int mCheatsLeft = 3;

    private TextView mAnswerTextView;
    private TextView mCheatsLeftTextView;
    private Button mShowAnswerButton;

    private boolean mPlayerCheated = false;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue, int cheatsLeft) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        intent.putExtra(EXTRA_CHEATS_LEFT, cheatsLeft);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mCheatsLeft = getIntent().getIntExtra(EXTRA_CHEATS_LEFT, 3);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mCheatsLeftTextView = (TextView) findViewById(R.id.cheat_cheats_left);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(v -> {
            // Only update if we haven't already
            if (!mPlayerCheated) {
                mCheatsLeft--;
                Toast.makeText(CheatActivity.this,
                        "Now you know.\nWas it worth it?\nPress back to go back.",
                        Toast.LENGTH_SHORT).show();
                userCheated();
            }
        });

        if (savedInstanceState != null) {
            // Ah, trying the ol' cheat and rotate trick?
            // Well, despite that obvious flaw in Android, it won't work!
            if (savedInstanceState.getBoolean(KEY_PLAYER_CHEATED)) {
                userCheated();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_PLAYER_CHEATED, mPlayerCheated);
        savedInstanceState.putInt(KEY_CHEATS_LEFT, mCheatsLeft);
    }

    private void userCheated() {
        mPlayerCheated = true;
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        mCheatsLeftTextView.setText("Cheats left: " + mCheatsLeft);
        setAnswerShownResult(true);
        mPlayerCheated = true;
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        // The book assumes false if there's an issue getting the intent back
        // I'll do that too, if only to fix a bug later.
        // But if it were up to me, CHEATERS GET NO MERCY!
        return result.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
}
