import java.util.Stack;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class DFDParser {
    public static void main(String[] args){
        Table<String, String, String> action = HashBasedTable.create(); //Use google Guava lib
        {
            action.put("1", "a", "s2");
            action.put("1", "b", "-");
            action.put("1", "c", "-");
            action.put("1", "d", "-");
            action.put("1", "e", "-");
            action.put("1", "$", "accept");

            action.put("2", "a", "-");
            action.put("2", "b", "s4");
            action.put("2", "c", "-");
            action.put("2", "d", "-");
            action.put("2", "e", "-");
            action.put("2", "$", "-");

            action.put("3", "a", "-");
            action.put("3", "b", "s6");
            action.put("3", "c", "-");
            action.put("3", "d", "s5");
            action.put("3", "e", "-");
            action.put("3", "$", "-");

            action.put("4", "a", "r3");
            action.put("4", "b", "r3");
            action.put("4", "c", "r3");
            action.put("4", "d", "r3");
            action.put("4", "e", "r3");
            action.put("4", "$", "r3");

            action.put("5", "a", "r4");
            action.put("5", "b", "r4");
            action.put("5", "c", "r4");
            action.put("5", "d", "r4");
            action.put("5", "e", "r4");
            action.put("5", "$", "r4");

            action.put("6", "a", "-");
            action.put("6", "b", "-");
            action.put("6", "c", "s7");
            action.put("6", "d", "-");
            action.put("6", "e", "-");
            action.put("6", "$", "-");

            action.put("7", "a", "r2");
            action.put("7", "b", "r2");
            action.put("7", "c", "r2");
            action.put("7", "d", "r2");
            action.put("7", "e", "r2");
            action.put("7", "$", "r2");

            action.put("8", "a", "-");
            action.put("8", "b", "-");
            action.put("8", "c", "-");
            action.put("8", "d", "-");
            action.put("8", "e", "s9");
            action.put("8", "$", "-");

            action.put("9", "a", "r1");
            action.put("9", "b", "r1");
            action.put("9", "c", "r1");
            action.put("9", "d", "r1");
            action.put("9", "e", "r1");
            action.put("9", "$", "r1");
        }//populate table
       //GOTO Table
        Table<String, String, String> goto_table = HashBasedTable.create(); //Use google Guava lib
        {
            goto_table.put("1", "A", "-");
            goto_table.put("1", "B", "-");
            goto_table.put("1", "S", "g1");

            goto_table.put("2", "A", "g3");
            goto_table.put("2", "B", "-");
            goto_table.put("2", "S", "-");

            goto_table.put("3", "A", "-");
            goto_table.put("3", "B", "g8");
            goto_table.put("3", "S", "-");
        } //populate table
        //input String to check
        char[] input={'a','b','b','c','d','e','$'};//change this
        //char[] input={'a','b','c','d','e','$'};//incorrect input
        //Define Grammar
        String[][] rules={
                {},
                {"S","aAbe"},
                {"A","Abc"},
                {"A","b"},
                {"B","d"}
        };
        Stack myStack=new Stack();//object type Stack
        int ptr=0;//keep track of input
        String word= String.valueOf(input[ptr]);
        myStack.push('1');
        while(true){
            //char s= myStack.peek();
            String s= String.valueOf(myStack.peek());
            char temp=action.get(s, word).charAt(0);
            if( temp == 's'){
                myStack.push(word);
                myStack.push(action.get(s, word).charAt(1));
                ptr += 1;
                word = String.valueOf(input[ptr]);
            }
            else if(temp=='r'){
                int rule_number=Integer.parseInt(String.valueOf(action.get(s, word).charAt(1)));
                String left_side=rules[rule_number][0];
                String right_side=rules[rule_number][1];
                int num=2*right_side.length();//op plus rule
                while(num!=0){
                    myStack.pop();
                    num-=1;
                }
                //char uncovered_s= (char) myStack.peek();
                String uncovered_s= String.valueOf(myStack.peek());
                myStack.push(left_side);//add non-terminal after reduction
                myStack.push(goto_table.get(uncovered_s, left_side).charAt(1));//fetch state number as we need to back
            }
            else if(action.get(s, word).equalsIgnoreCase("accept")){
                System.out.println("Accepted!");
                break;
            }
            else{
                System.out.println("Rejected!");
                break;
            }
        }
        //System.out.println(action.get("1","a").charAt(0));
    }
}
