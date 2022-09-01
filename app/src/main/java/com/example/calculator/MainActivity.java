package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView output;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bExp,bEqual,bAdd,bMinus,bMultiply,bDevide,bAc,bMod,bPoint,bP1,bP2,bBack;
    Switch modeSwitch;
    boolean isDay=false;
    Set<Character> operators = new HashSet<Character>();
    String inorderExression="";

    private void initializeAll(){
        operators.add('+');operators.add('-');operators.add('*');operators.add('/');operators.add('^');operators.add('%');
        output=findViewById(R.id.output);
        b0=findViewById(R.id.Btn0);
        b1=findViewById(R.id.Btn1);
        b2=findViewById(R.id.Btn2);
        b3=findViewById(R.id.Btn3);
        b4=findViewById(R.id.Btn4);
        b5=findViewById(R.id.Btn5);
        b6=findViewById(R.id.Btn6);
        b7=findViewById(R.id.Btn7);
        b8=findViewById(R.id.Btn8);
        b9=findViewById(R.id.Btn9);
        bExp=findViewById(R.id.BtnExponential);
        bAc=findViewById(R.id.Btnac);
        bEqual=findViewById(R.id.BtnEqual);
        bAdd=findViewById(R.id.BtnPlus);
        bPoint=findViewById(R.id.BtnPoint);
        bMod=findViewById(R.id.BtnMod);
        bMinus=findViewById(R.id.BtnMinus);
        bMultiply=findViewById(R.id.BtnMultiply);
        bDevide=findViewById(R.id.BtnDevide);
        bP1=findViewById(R.id.BtnP1);
        bP2=findViewById(R.id.BtnP2);
        bBack=findViewById(R.id.BtnBack);
    }
    private double binaryExp(double base, int power){
        if(power==0)return 1;
        if((power & 1)==1){
            return base*binaryExp(base,power-1);
        }
        double res=binaryExp(base,power/2);
        return res*res;


    }
    static int precedence(char c)
    {
        switch (c)
        {
            case '+':
            case '-':
            case '%':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;

        }

        return -1;
    }
    public double evaluate(String exp) throws Exception
    {
        //operators.add('+');operators.add('-');operators.add('*');operators.add('/');operators.add('^');operators.add('%');
        Stack<Double> operands = new Stack<Double>();
        Stack<Character> operations = new Stack<Character>();
        int i=0;
        while(i<exp.length()){
            char c = exp.charAt(i);
            int start=i;
            if(! operators.contains(c) && c!='(' && c!=')')
            {

                while (! operators.contains(c) && c!='(' && c!=')' ) {

                    i++;
                    if(( i== exp.length())) {break;}
                    c = exp.charAt(i);
                }

                double num= Double.parseDouble(exp.substring(start,i));
                operands.push(num);
                if(i==exp.length()){break;}


            }

            if(c=='(')
            {
                operations.push(c);
            }
            if(c==')')
            {
                while(operations.peek()!='(')
                {
                    double outputNum = performOperation(operands, operations);
                    operands.push(outputNum);
                }
                operations.pop();
            }


            if(operators.contains(c))
            {
                if(operations.size()<1){
                    operations.push(c);

                }
                else{
                    while( precedence(c)<=precedence(operations.peek()))
                    {
                        double outputNum = performOperation(operands, operations);
                        operands.push(outputNum);
                        if(operations.size()<1){
                            break;

                        }
                    }
                    operations.push(c);
                }
            }
            i++;
        }

        while(!operations.isEmpty())
        {
            double outputNum = performOperation(operands, operations);
            operands.push(outputNum);
        }
        return operands.pop();
    }

    public double performOperation(Stack<Double> operands, Stack<Character> operations) throws Exception
    {
        double a = operands.pop();
        double b = operands.pop();
        char operation = operations.pop();
        switch (operation)
        {
            case '+':
                return a + b;
            case '-':
                return b - a;
            case '*':
                return a * b;
            case '%':
                return b%a;
            case '/':
                if (a == 0)
                {

                    return 0;
                }
                return b / a;

        }
        return binaryExp(b,(int)a);
    }
    void showInorder(String s){
        inorderExression+=s;
        output.setText(inorderExression);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem itemswitch = menu.findItem(R.id.switch_action_bar);
        itemswitch.setActionView(R.layout.use_switch);

        final Switch sw = (Switch) menu.findItem(R.id.switch_action_bar).getActionView().findViewById(R.id.switch2);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeAll();


        output.setText("");

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inorderExression.length()==0) return;
                inorderExression= inorderExression.substring(0, inorderExression.length()-1);
                showInorder("");
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b0.getText().toString());
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b1.getText().toString());

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b2.getText().toString());

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b3.getText().toString());

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b4.getText().toString());

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b5.getText().toString());

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b6.getText().toString());

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b7.getText().toString());
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b8.getText().toString());

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(b9.getText().toString());
            }
        });
        bPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bPoint.getText().toString());

            }
        });
        bExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bExp.getText().toString());

            }
        });
        bEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    double ans= evaluate(inorderExression);
                    inorderExression="";
                    showInorder(""+ans);
                }
                catch (Exception e){
                    inorderExression="";
                    showInorder("invalid expression");
                }
                finally {
                    inorderExression="";
                }



            }
        });
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bAdd.getText().toString());


            }
        });
        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bMinus.getText().toString());
            }
        });
        bMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bMultiply.getText().toString());

            }
        });
        bDevide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bDevide.getText().toString());
            }
        });
        bMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bMod.getText().toString());

            }
        });
        bAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inorderExression="";
                showInorder("");

            }
        });

        bP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inorderExression.length()==0 || operators.contains(inorderExression.charAt(inorderExression.length()-1))) {
                    showInorder(bP1.getText().toString());
                    return;
                }
                showInorder("*(");

            }
        });

        bP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInorder(bP2.getText().toString());

            }
        });
    }

}
