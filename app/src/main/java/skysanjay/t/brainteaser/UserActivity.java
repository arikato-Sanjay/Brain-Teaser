package skysanjay.t.brainteaser;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class UserActivity extends AppCompatActivity {

    TextView questionTV;
    ArrayList<Integer> answer = new ArrayList<>();
    Button option1,option2,option3,option4,playAgainButton;
    int positionOFCorrectAnswer;
    TextView resultTV,scoreTV,timer;
    int score = 0;
    int totalQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        questionTV = findViewById(R.id.sumTV);
        option1 = findViewById(R.id.opt1);
        option2 = findViewById(R.id.opt2);
        option3 = findViewById(R.id.opt3);
        option4 = findViewById(R.id.opt4);
        resultTV = findViewById(R.id.resultTV);
        scoreTV = findViewById(R.id.scoreTV);
        timer = findViewById(R.id.timerTV);
        playAgainButton = findViewById(R.id.playAgainButton);

        playAgain(findViewById(R.id.scoreTV));

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain(findViewById(R.id.scoreTV));
                resultTV.setText("");
                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                option4.setEnabled(true);
            }
        });
    }

    public void chooseAnswer(View view){
        if (Integer.toString(positionOFCorrectAnswer).equals(view.getTag())){
            resultTV.setText("Correct :)");
            score++;
        } else {
            resultTV.setText("Incorrect :(");
        }
        totalQuestions++;
        scoreTV.setText(score+"/"+totalQuestions);
        nextGame();
    }

    public void nextGame(){
        Random random = new Random();
        int op1 = random.nextInt(21);
        int op2 = random.nextInt(21);
        questionTV.setText(op1 + "+" + op2);

        positionOFCorrectAnswer = random.nextInt(4);
        answer.clear();
        for (int i=0;i<4;i++){
            if (i == positionOFCorrectAnswer) {
                answer.add(op1+op2);
            } else {
                int incorrectAnswer = random.nextInt(41);
                while (incorrectAnswer == op1+op2){
                    incorrectAnswer = random.nextInt(41);
                }
                answer.add(incorrectAnswer);
            }
        }

        option1.setText(String.valueOf(answer.get(0)));
        option2.setText(String.valueOf(answer.get(1)));
        option3.setText(String.valueOf(answer.get(2)));
        option4.setText(String.valueOf(answer.get(3)));
    }

    public void playAgain(View view){
        score = 0;
        totalQuestions = 0;
        timer.setText("30s");
        scoreTV.setText(score+"/"+totalQuestions);
        nextGame();
        playAgainButton.setVisibility(View.INVISIBLE);
        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished/1000 + "s");
            }

            @Override
            public void onFinish() {
                resultTV.setText("Done!");
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);
                playAgainButton.setVisibility(View.VISIBLE);
                //MediaPlayer sound = MediaPlayer.create(getApplicationContext(),R.raw.endsound);
            }
        }.start();
    }
}
