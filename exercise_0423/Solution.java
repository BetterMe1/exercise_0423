package exercise.exercise_0423;

import java.util.Scanner;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String infix = sc.nextLine();
        System.out.println(check(infix));
       /* String suffix = intoSuffix(infix);
        System.out.println("后缀表达式："+suffix);
        System.out.println("计算结果："+calculateSuffix(suffix));*/
    }
    public static String check(String infix){
        //1.添加空格
        StringBuffer sb = new StringBuffer();
        for(int i=0,len=infix.length(); i<len; i++) {
            char tmp = infix.charAt(i);
            if (tmp > '9' || tmp < '0') {//操作符
               sb.append(String.valueOf(tmp)).append(" ") ;
            }else{
                int j=i;
                while(i<len && '0'<=infix.charAt(i) && infix.charAt(i)<='9') {
                    i++;
                }
                sb.append(infix.substring(j,i)).append(" ");
                i--;
            }
        }
        String[] str = sb.toString().split(" ");
       //2.检验中缀表达式是否合法
        Stack<String> stack = new Stack<>();
        //2.1操作符不能连续
        return sb.toString();
    }
    public static String intoSuffix(String infix){
        Stack<String> stack1 = new Stack<>();//放操作符的栈
        Stack<String> stack2 = new Stack<>();//放数字的栈
        for(int i=0,len = infix.length(); i<len; i++){
            char tmp = infix.charAt(i);
            if(tmp>'9' || tmp<'0'){//操作符
                if(tmp != ')'){//不等于‘)'的操作符
                    stack1.push(String.valueOf(tmp));
                }else{//‘)'
                    String mark = stack1.pop();//弹出操作符
                    while (mark != "(" && !stack1.empty()){
                        //弹出两个数字
                        String number2 = stack2.pop();
                        String number1 = stack2.pop();
                        String result = number1+" "+number2+" "+mark;//结果
                        stack2.push(result);//结果入栈
                        mark = stack1.pop();//更新操作符
                    }
                }
            }else{//数字
                String tmp2;//存结果
                int j=i;
                while(i<len && '0'<=infix.charAt(i) && infix.charAt(i)<='9'){
                    i++;
                }
                tmp2 = infix.substring(j,i);
                stack2.push(tmp2);
                i--;
            }
        }
        while(!stack1.empty()){
            String mark = stack1.pop();//弹出操作符
            String number2 = stack2.pop();//弹出两个数字
            String number1 = stack2.pop();
            String result = number1+" "+number2+" "+mark;//结果
            stack2.push(result);//结果入栈
        }
        return stack2.pop();
    }
    //1 2 3 4 * + + 5 /
    public static int calculateSuffix(String suffix){
       String[] s = suffix.split(" ");
      /* for(int i=0; i<s.length; i++){
           System.out.println(s[i]);
       }*/
       Stack<Integer> stack = new Stack<>();
       for(int i=0; i<s.length; i++){
           if(("+".equals(s[i])) || ("-".equals(s[i])) || ("*".equals(s[i])) || ("/".equals(s[i]))){//操作符
               int number2 = stack.pop();
               int number1 = stack.pop();
               int result = 0;
               if("+".equals(s[i])){
                   result = number1+number2;
               }else if("-".equals(s[i])){
                   result = number1-number2;
               }else if("*".equals(s[i])){
                   result = number1*number2;
               }else if("/".equals(s[i])){
                   result = number1/number2;
               }
               stack.push(result);
           }else{//数字
               stack.push(Integer.parseInt(s[i]));
           }
       }
       return stack.pop();
    }
}
