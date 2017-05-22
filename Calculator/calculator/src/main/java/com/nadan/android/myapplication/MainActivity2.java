package com.nadan.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-05-22.
 */

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    EditText txtPreview, txtResult;

    //객체 참조
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼은 참조하는 방식과 이름을 만드는 형식이 동일하기 때문에 순환문을 통해 코드를 간결하게 할 수 있다.
        for(int i=0; i<10; i++){
            int id = getResources().getIdentifier("btn_"+i, "id", getPackageName());
            findViewById(id).setOnClickListener(this);
        }

        //연산자 버튼 참조
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_sub).setOnClickListener(this);
        findViewById(R.id.btn_multi).setOnClickListener(this);
        findViewById(R.id.btn_div).setOnClickListener(this);
        findViewById(R.id.btn_clean).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);

        //창 버튼 참조
        txtPreview = (EditText) findViewById(R.id.window);
        txtResult = (EditText) findViewById(R.id.inputNumber);
    }

    // Preview 화면에 식이 나타나도록 하는 메소드
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_0: showPreview(0); break;
            case R.id.btn_1: showPreview(1); break;
            case R.id.btn_2: showPreview(2); break;
            case R.id.btn_3: showPreview(3); break;
            case R.id.btn_4: showPreview(4); break;
            case R.id.btn_5: showPreview(5); break;
            case R.id.btn_6: showPreview(6); break;
            case R.id.btn_7: showPreview(7); break;
            case R.id.btn_8: showPreview(8); break;
            case R.id.btn_9: showPreview(9); break;
            case R.id.btn_add: showPreview("+"); break;
            case R.id.btn_sub: showPreview("-"); break;
            case R.id.btn_multi: showPreview("*"); break;
            case R.id.btn_div: showPreview("/"); break;
            case R.id.btn_clean: clear(); break;
            case R.id.btn_equal: result(); break;
            case R.id.btn_delete: delete(); break;


        }
    }

    //보여주기 방식이 동일하기 때문에 따로 메소드로 뺴낸다.
    public void showPreview(int number){
        txtPreview.append(number+"");
    }

    //오버로딩의 아주 좋은 예. 같은 기능이지만 매개변수 타입만 다르다
    public void showPreview(String operator){
        txtPreview.append(operator);
    }

    //계산된 값을 화면에 보여준다.
    public void result(){
        String current = txtPreview.getText().toString();
        txtResult.setText(calculate(current));
    }

    //값을 모두 초기화
    public void clear(){
        txtPreview.setText("");
        txtResult.setText("");
    }

    //계산식만 초기화
    public void delete(){
        txtPreview.setText("");
    }

    //계산
    public String calculate(String preview){

        //정규식을 통해 연산자와 숫자를 분리해낸다.
        String[] split = preview.split("(?<=[*/+-])|(?=[*/+-])");

        //배열에서 수를 빼낼 때 유연하게 사용하기 위해 컬렉션을 이용한다.
        ArrayList<String> list = new ArrayList<>();
        for( String str : split){
            list.add(str);
        }

        //가장 먼저 곱셈과 나눗셈을 계산한다.
        for(int i=0; i<list.size(); i++){ //여기서 int size = list.size()로 하면 배열의 크기보다 큰 수가 들어가 문제가 생긴다.
            //굳이 하나하나 다 구할 필요 없이, 일단 *, /이 나타날 경우 앞 뒤 계산만 해서 넣어주면 된다.
            String temp = list.get(i).toString(); //list.get(i) == "+"로 하지 않고, equals를 쓰는 게 좋다!!!
            if(temp.equals("*") || temp.equals("/")) {
                String before = list.get(i-1); //연산자 앞 항
                String after = list.get(i+1); //연산다 뒤 항
                double current = 0; //계산 값
                if(temp.equals("*")){ //곱셈일 경우
                    current = Double.parseDouble(before)*Double.parseDouble(after);
                } else if(temp.equals("/")){ //나눗셈일 경우
                    current = Double.parseDouble(before)/Double.parseDouble(after);
                }

                list.set(i, current+""); //세 항이 없어지고 한 항이 생기는데, 세 항 중 첫 항으로 들어간다.
                list.remove(i+1); //먼저 세번째 항을 없애고
                list.remove(i-1); //나중에 첫 항을 없애야 엉뚱한 값을 제거하지 않는다.
                i--;
            }
        }

        //남은 항은 이제 덧셈과 뺄셈
        for(int i=0; i<list.size(); i++){
            String temp = list.get(i).toString(); //list.get(i) == "+"는 왜 안 되는가?
            if(temp.equals("+") || temp.equals("-")) {
                String before = list.get(i-1); //연산자 앞 항
                Log.w("MainActivity2", before);
                String after = list.get(i+1); //연산다 뒤 항
                Log.w("MainActivity2", after);
                double current = 0; //계산 값
                if(temp.equals("+")){ //덧셈일 경우
                    current = Double.parseDouble(before)+Double.parseDouble(after);
                } else if(temp.equals("-")){ //뺄셈일 경우
                    current = Double.parseDouble(before)-Double.parseDouble(after);
                }
                list.set(i, current+"");
                list.remove(i+1);
                list.remove(i-1);
                i--;
            }
        }
        return list.get(0);
    }
}
