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

public class MainActivity extends AppCompatActivity {

    TextView output;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bExp,bEqual,bAdd,bMinus,bMultiply,bDevide,bAc,bMod,bPoint,bToggleMode;
    Switch modeSwitch;
    boolean hasFirstExp=false;
    String firstExp="";
    String secondExp="";
    char operation='e';
    boolean isDay=false;
    int toggleClick=0;

    private void initializeAll(){
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

    }

    private double binaryExp(double base, int power){
        if(power==0)return 1;
        if((power & 1)==1){
            return base*binaryExp(base,power-1);
        }
        double res=binaryExp(base,power/2);
        return res*res;


    }
    public double evaluate(double num1, double num2, char operation){
        if(operation=='+'){
            return num1+num2;
        }
        if(operation=='-'){
            return num1-num2;
        }

        if(operation=='*'){
            return num1*num2;
        }
        if(operation=='/'){
            if(num2==0)
                return  0;
            return num1/num2;
        }
        if(operation=='%'){
            return num1%num2;
        }
        // else to the power
        return binaryExp(num1,(int)num2);

    }

    void evaluateExpression(){
        // user pressed equal button without any operator or second expression
        if(operation=='e' || secondExp==""){
            hasFirstExp=firstExp!="";
            return;
        }

        if(hasFirstExp){
            double firstExpFloat=Double.parseDouble(firstExp);
            double secondExpFloat=Double.parseDouble(secondExp);
            double ans= evaluate(firstExpFloat,secondExpFloat,this.operation);
            firstExp=""+ans;
            secondExp="";

            hasFirstExp=false;
            operation='e';
            return;
        }



    }
    void showOperator(){
        hasFirstExp=true;
        output.setText(""+this.operation);
    }
    void show(){
        if(!hasFirstExp ){
            output.setText(""+firstExp);
            return;
        }
        output.setText(""+secondExp);

    }
    void addToExp(char c){
        if(!hasFirstExp){
            firstExp+=c;
            return;
        }
        secondExp+=c;
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



        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('0');
                show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('1');
                show();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('2');
                show();

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('3');
                show();

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('4');
                show();

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('5');
                show();

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('6');
                show();

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('7');
                show();

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('8');
                show();

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('9');
                show();

            }
        });
        bPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToExp('.');
                show();

            }
        });
        bExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation='^';
                showOperator();

                    evaluateExpression();

            }
        });
        bEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluateExpression();
                show();

                // will start from first again if this code is executed
                // else saves the output as first expression

                /*

                firstExp="";
                secondExp="";
                hasFirstExp=false;

                 */


            }
        });
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation='+';
                showOperator();
                evaluateExpression();


            }
        });
        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation='-';
                showOperator();
                evaluateExpression();


            }
        });
        bMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation='*';
                showOperator();
                evaluateExpression();

            }
        });
        bDevide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation='/';
                showOperator();
                evaluateExpression();
            }
        });
        bMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation='%';
                showOperator();
                evaluateExpression();

            }
        });
        bAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstExp="";
                secondExp="";
                hasFirstExp=false;
                operation='e';
                show();

            }
        });


    }

}
