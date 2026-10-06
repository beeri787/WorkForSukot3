package com.example.workforsukot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.BreakIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int count=0;
    int questionNumber=1;
    int number1;
    int number2;
    String symbol;
    int correctAnswer;

    private void generateQuestion() {
        // generate numbers
        Random random1 = new Random();
        number1 = random1.nextInt(100) + 1; // Removed "int"
        Random random2 = new Random();
        number2 = random2.nextInt(100) + 1; // Removed "int"
        TextView firstNumber = findViewById(R.id.firstNumber);
        firstNumber.setText(String.valueOf(number1));
        TextView secondNumber = findViewById(R.id.secondNumber);
        secondNumber.setText(String.valueOf(number2));

        // generate symbol
        String[] symbols = {"+", "-", "*"};
        Random random3 = new Random();
        symbol = symbols[random3.nextInt(symbols.length)]; // Removed "String"
        TextView mathSymbol = findViewById(R.id.mathSymble);
        mathSymbol.setText(symbol);

        // calculate correct answer
        if (symbol.equals("+"))
            correctAnswer = number1 + number2;
        else if (symbol.equals("-")) {
            correctAnswer = number1 - number2;
        }
        else if (symbol.equals("*")) {
            correctAnswer = number1 * number2;
        }

        // display question
        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        mathSymbol = findViewById(R.id.mathSymble);

        firstNumber.setText(String.valueOf(number1));
        secondNumber.setText(String.valueOf(number2));
        mathSymbol.setText(symbol);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button checkButton = findViewById(R.id.check);
        generateQuestion();

        checkButton.setOnClickListener(v -> {
            //answer
            EditText answerInput = findViewById(R.id.answer);
            String text = answerInput.getText().toString();
            if(text.isEmpty()) {
                Toast.makeText(this, "Enter an answer", Toast.LENGTH_SHORT).show();
                return;
            }
            int answer = Integer.parseInt(text);

            //checkifright
            if (answer == correctAnswer)
            {
                count++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                questionNumber++;
                generateQuestion();
            }
            else {
                Toast.makeText(this, "False!", Toast.LENGTH_SHORT).show();
                questionNumber++;
                generateQuestion();
            }

            //stop the app
            if (questionNumber > 10) {
                TextView result = findViewById(R.id.result);
                result.setText("Your score is: " + count + "/10");
                checkButton.setEnabled(false);
                answerInput.setEnabled(false);
            }
        });

        // FIXED LINE BELOW: Changed R.id.mathSymble to R.id.main
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}