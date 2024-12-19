package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView calcDisplay;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private double lastResult = 0;
    char operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        calcDisplay = findViewById(R.id.calc_display);
        calcDisplay.setText("");
    }

    public void onPress(View view){
        Button button = (Button) view;
        String str = button.getText().toString();
        calcDisplay.append(str);
    }

    public void onOperator(View view){
        Button button = (Button) view;
        String text = calcDisplay.getText().toString().trim();

        if (text.isEmpty() || !isNumeric(text)) {
            calcDisplay.setText("Enter a valid number");
            return;
        }

        if (operator != '\0') {
            operator = button.getText().toString().charAt(0);
            return;
        }

        firstNumber = Double.parseDouble(text);
        System.out.println("firstNumber: " + firstNumber);
        operator = button.getText().toString().charAt(0);
        calcDisplay.setText("");
    }

    public void onEqual(View view){
        String text = calcDisplay.getText().toString().trim();

        if (text.isEmpty() || !isNumeric(text)) {
            calcDisplay.setText("Enter a valid second number");
            return;
        }

        secondNumber = Double.parseDouble(text);
        System.out.println("secondNumber: " + secondNumber);
        lastResult = calculate(firstNumber, secondNumber, operator);
        System.out.println("result: " + lastResult);
        calcDisplay.setText(String.valueOf(lastResult));

        operator = '\0';
    }

    public void onAnswer(View view){
        calcDisplay.setText(String.valueOf(lastResult));
    }

    public void onClear(View view){
        firstNumber = secondNumber = 0;
        operator = '\0';
        calcDisplay.setText("");
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double calculate(double num1, double num2, char operator){
        switch (operator){
            case '+':
                lastResult = num1 + num2;
                break;
            case '-':
                lastResult = num1 - num2;
                break;
            case 'X':
                lastResult = num1 * num2;
                break;
            case '/':
                if(num2 != 0){
                lastResult = num1 / num2;
                } else{
                    calcDisplay.setText("Error");
                    return Double.NaN;
                }
                break;
            case '^':
                lastResult = Math.pow(firstNumber, secondNumber);
                break;
            default:
                lastResult = Math.pow(firstNumber, 1/secondNumber);
        }
        firstNumber = lastResult;
        return lastResult;
    }
}