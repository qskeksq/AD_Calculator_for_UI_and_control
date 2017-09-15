# AD_Calculator_for_UI

### 사칙연산 계산기
- 정규식 사용의 이해
- 컬렉션 ConcurrentModificationException, size() 변화
- MVP 기초적 적용
- try-catch로 연산 오류 처리

![myCal2](https://github.com/qskeksq/AD_Calculator_for_UI_and_control/blob/master/myCal2.png?raw=true)
![myCal1](https://github.com/qskeksq/AD_Calculator_for_UI_and_control/blob/master/myCal1.png?raw=true)


  ```java
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
  ```
