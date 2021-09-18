package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView displayText;
    TextView resultText;

    String display = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    private void initTextViews(){
        displayText = (TextView)findViewById(R.id.displayText);
        resultText = (TextView)findViewById(R.id.resultText);
    }

    private void setDisplay(String value){
        // main == display
        // result == second
        display = displayText.getText().toString();
        displayText.setText(display + value);
    }

    public void zeroButton(View view){
        setDisplay("0");
    }
    public void oneButton(View view){
        setDisplay("1");
    }
    public void twoButton(View view){
        setDisplay("2");
    }
    public void threeButton(View view){
        setDisplay("3");
    }
    public void fourButton(View view){
        setDisplay("4");
    }
    public void fiveButton(View view){
        setDisplay("5");
    }
    public void sixButton(View view){
        setDisplay("6");
    }
    public void sevenButton(View view){
        setDisplay("7");
    }
    public void eightButton(View view){
        setDisplay("8");
    }
    public void nineButton(View view){
        setDisplay("9");
    }
    public void deleteButton(View view){
        displayText.setText("");
        display = "";
        displayText.setText("");
        resultText.setText("");
    }
    public void modulusButton(View view){
        setDisplay("%");
    }

    public void backspaceButton(View view){
        StringBuffer sb= new StringBuffer(display);
        display = "";
        sb.deleteCharAt(sb.length()-1);
        display = String.valueOf(sb);
        displayText.setText(display);
    }

    public void divideButton(View view){
        setDisplay("/");
    }
    public void mutiplyButton(View view){
        setDisplay("*");
    }
    public void negatifButton(View view){
        setDisplay("-");
    }
    public void plusButton(View view){
        setDisplay("+");
    }

    public void equalsButton(View view){
        String value = displayText.getText().toString();
        String replaceString = value.replace('÷','/').replace('×','*');
        double result = calculate(replaceString);
        resultText.setText(String.valueOf(result));
        displayText.setText(value);
    }

    public void pointButton(View view){
        setDisplay(".");
    }

    public void factorialButton(View view){
        String value = displayText.getText().toString();
        int valueInt = 0;
        if(value.equals("")){
            resultText.setText("Error");
        }
        else {
            valueInt = Integer.parseInt(value);
            int factorial = factorial(valueInt);
            resultText.setText(String.valueOf(factorial));
            displayText.setText(value+"!");
        }
    }

    int factorial(int n){
        return (n == 1 || n == 0 ? 1 : n * factorial(n-1));
    }

    public static double calculate(final String str) {
        return new Object() {
            int position = -1, character;

            void nextCharacter() {
                character = (++position < str.length()) ? str.charAt(position) : -1;
            }

            boolean eat(int charToEat) {
                while (character == ' ') nextCharacter();
                if (character == charToEat) {
                    nextCharacter();
                    return true;
                }
                return false;
            }

            double parse() {
                nextCharacter();
                double x = parseExpression();
                if (position < str.length()) throw new RuntimeException("Unexpected: " + (char) character);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.position;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((character >= '0' && character <= '9') || character == '.') {
                    while ((character >= '0' && character <= '9') || character == '.') nextCharacter();
                    x = Double.parseDouble(str.substring(startPos, this.position));
                } else if (character >= 'a' && character <= 'z') {
                    while (character >= 'a' && character <= 'z') nextCharacter();
                    String func = str.substring(startPos, this.position);
                    x = parseFactor();
                } else {
                    throw new RuntimeException("Unexpected: " + (char) character);
                }
                return x;
            }
        }.parse();
    }
}