package exercise.exercise_0423;

import java.util.Scanner;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str= sc.nextLine();
        System.out.print("中缀表达式：");
        String infix = check(str);
        if(infix == null){
            System.out.println("表达式不合法");
        }else{
            System.out.println(infix);
            String suffix = intoSuffix(infix);
            System.out.println("后缀表达式："+suffix);
            System.out.println("计算结果："+calculateSuffix(suffix));
        }
    }
    public static String check(String s){
        if(s == null || s.length() == 0){
            return null;
        }
        //1.添加空格
        StringBuffer sb = new StringBuffer();
        for(int i=0,len=s.length(); i<len; i++) {
            char tmp = s.charAt(i);
            if (tmp > '9' || tmp < '0') {//操作符
                if(tmp == '-'&&(i==0 ||(i!=0 && s.charAt(i-1)=='('))){//负数的-号
                    int j=i;
                    i++;
                    while(i<len && '0'<=s.charAt(i) && s.charAt(i)<='9') {
                        i++;
                    }
                    sb.append(s.substring(j,i)).append(" ");
                    i--;
                }else{
                    sb.append(String.valueOf(tmp)).append(" ") ;
                }
            }else{
                int j=i;
                while(i<len && '0'<=s.charAt(i) && s.charAt(i)<='9') {
                    i++;
                }
                sb.append(s.substring(j,i)).append(" ");
                i--;
            }
        }
        String[] str = sb.toString().split(" ");
       //2.检验中缀表达式是否合法
        //2.1操作符"+"，"-"，"*"，"/"不能出现在首位，末位
        if("+".equals(str[0]) || "-".equals(str[0]) ||"*".equals(str[0]) ||"/".equals(str[0]) ||
                "+".equals(str[str.length-1]) || "-".equals(str[str.length-1])
                ||"*".equals(str[str.length-1]) ||"/".equals(str[str.length-1]) ){
            return null;
        }
        //2.2操作符"+"，"-"，"*"，"/"“不能连续,括号成对匹配
        Stack<String> stack = new Stack<>();
        for(int i=0; i<str.length; i++){
            if("(".equals(str[i])){//左括号入栈
                stack.push(str[i]);
            }else if(")".equals(str[i])){//右括号出栈
                if(!("(".equals(stack.pop()))){
                    return null;
                }
            }else if(("+".equals(str[i]) || "-".equals(str[i]) ||"*".equals(str[i]) ||"/".equals(str[i]))
            && ("+".equals(str[i-1]) || "-".equals(str[i-1]) ||"*".equals(str[i-1]) ||"/".equals(str[i-1]))){//连续操作符
                return null;
            }
        }
        return stack.empty() ? sb.toString() : null;//栈不为空说明括号不匹配
    }
    public static String intoSuffix(String infix){
        Stack<String> stack1 = new Stack<>();//放操作符的栈
        Stack<String> stack2 = new Stack<>();//放数字的栈
        String[] str = infix.split(" ");
        for(int i=0,len = str.length; i<len; i++){
            String tmp = str[i];
            if(")".equals(str[i])) {
                String mark = stack1.pop();//弹出操作符
                while ((!"(".equals(mark)) && (!stack1.empty())) {
                    //弹出两个数字
                    String number2 = stack2.pop();
                    String number1 = stack2.pop();
                    String result = number1 + " " + number2 + " " + mark;//结果
                    stack2.push(result);//结果入栈
                    mark = stack1.pop();//更新操作符
                }
                while(!stack1.empty() && ("*".equals(stack1.peek()) ||"/".equals(stack1.peek()))){
                    //弹出两个数字
                    String number2 = stack2.pop();
                    String number1 = stack2.pop();
                    String mark1 = stack1.pop();
                    String result = number1 + " " + number2 + " " + mark1;//结果
                    stack2.push(result);//结果入栈
                }
            }else if("+".equals(str[i]) || "-".equals(str[i]) ||
                    "*".equals(str[i]) ||"/".equals(str[i])||"(".equals(str[i])){//操作符
                stack1.push(tmp);
            }
            else{//数字
                stack2.push(str[i]);
                while(!stack1.empty() && ("*".equals(stack1.peek()) ||"/".equals(stack1.peek()))){
                    //弹出两个数字
                    String number2 = stack2.pop();
                    String number1 = stack2.pop();
                    String mark1 = stack1.pop();
                    String result = number1 + " " + number2 + " " + mark1;//结果
                    stack2.push(result);//结果入栈
                }
            }
        }
        Stack<String> stack11 = new Stack<>();//放操作符
        Stack<String> stack22 = new Stack<>();//放数字
        while(!stack1.empty()){
            stack11.push(stack1.pop());
        }
        while(!stack2.empty()){
            stack22.push(stack2.pop());
        }
        while(!stack11.empty()){
            String mark = stack11.pop();//弹出操作符
            String number1 = stack22.pop();//弹出两个数字
            String number2 = stack22.pop();
            String result = number1+" "+number2+" "+mark;//结果
            stack22.push(result);//结果入栈
        }
        return stack22.pop();
    }
    public static int calculateSuffix(String suffix){
       String[] s = suffix.split(" ");
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


