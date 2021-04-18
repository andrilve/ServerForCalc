package Calc;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

public class JThread extends Thread {

    private final HashMap<String, Function<Double[], Double>> mathOperator;
    public Queue<String> queue;
    private Double out;
    public String equalSign = "=";

    public JThread( Queue<String> queue, HashMap<String, Function<Double[], Double>> mathOperators ) {
        this.queue = queue;
        this.mathOperator = mathOperators;
    }

    public void run() {

        Thread current = Thread.currentThread();

        while (true) {
            //остоновка потока после завершения main
            if (!current.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Работа потока была прервана");
                    break;
                }
            }

            HashMap<String, Integer> signH = new HashMap<>();
            signH.put("(", 3);
            signH.put(")", 4);
            signH.put("=", 5);
            signH.put("*", 2);
            signH.put("/", 2);
            signH.put("+", 1);
            signH.put("-", 1);

            //if (queue.size() > 0) {
            out = doAlgoritm(queue, signH, 1);
                //doSomething()
                //System.out.println("очередь" + elementQueue);
                //if (mathOperator.containsKey(elementQueue)) {
                    //stackMathOp.push(elementQueue);
                    //System.out.println("знак+");
                //} else {
                   // stackNumb.push(elementQueue);
                   // while (stackNumb.size() > 1) {
                       // double secondValue = Double.parseDouble(stackNumb.pop());
                        //double firstValue = Double.parseDouble(stackNumb.pop());
                        //String mathOp = stackMathOp.pop();
                        //if (mathOp.equals("+")) {
                           // Double[][] twoDimArray = new Double[1][2];
                            //twoDimArray[0][0] = firstValue;
                           // twoDimArray[0][1] = secondValue;
                            //out = mathOperator.get("+").apply(twoDimArray);
                            //Out.otpravitDannuu(out);
                            //stackNumb.push(String.valueOf(out));
                            //stackNumb.push(MathCalcMoi.plus(firstValue, secondValue));
                       // }
                   // }
               // }
            //}
        }
    }

    public Double doAlgoritm(Queue<String> queue, HashMap<String, Integer> signH, int count) {

        Stack<Double> stackNumb = new Stack<>();
        Stack<String> stackSigne = new Stack<>();

        Integer priority = null;

        boolean flag = true;
        while(flag){
            System.out.println("Да схерли что происходит");
            while (queue.size() > 0) {
                System.out.println("может ты один раз попала и больше попадать не хочешь?");
                String value = queue.poll();
                priority = signH.get(value);
                if (priority != null) {
                    /* the sign stack is empty, just put first sign to it */
                    if (stackSigne.size() == 0) {
                        stackSigne.push(value);
                    }
                    /* we start calculating of value in brackets separately*/
                    else if (priority == signH.get("(")) {
                        stackNumb.push(doAlgoritm(queue, signH, count + 1));
                    }
                    /* the end of expression, the result should be provided */
                    else if (priority == signH.get(")") || priority == signH.get(equalSign)) {
                        calculate(stackNumb, stackSigne);
                        return stackNumb.pop();
                    }
                    /* the new operator have higher priority, put it to stack */
                    else if (priority > signH.get(stackSigne.peek())) {
                        stackSigne.push(value);
                    }
                    /* the previous operator has higher priority and can be executed as other operators with same priority*/
                    else {
                        while (stackNumb.size() > 1 && priority <= signH.get(stackSigne.peek())) {
                            Double secondValue = stackNumb.pop();
                            Double firstValue = stackNumb.pop();
                            String signe = stackSigne.pop();
                            out = execute(firstValue, secondValue, signe);
                            stackNumb.push(out);
                            System.out.println("какого хрена ты не работаешь");
                            Out.otpravitDannuu(out);
                        }
                        stackSigne.push(value);
                    }
                } else {
                    stackNumb.push(Double.parseDouble(value));
                }
            }
        }

        return null;
    }

    private void calculate( Stack<Double> numStack, Stack<String> signStack ) {
        while (numStack.size() > 1) {
            Double secondValue = numStack.pop();
            Double firstValue = numStack.pop();
            String sign = signStack.pop();
            out = execute(firstValue, secondValue, sign);
            numStack.push(out);
            Out.otpravitDannuu(out);
        }
    }

    private static Double execute( Double firstNum, Double secondNum, String operSign ) {
        if (operSign.equals("+")) {
            return (firstNum + secondNum);
        } else if (operSign.equals("-")) {
            return (firstNum - secondNum);
        } else if (operSign.equals("*")) {
            return (firstNum * secondNum);
        } else if (operSign.equals("/")) {
            return (firstNum / secondNum);
        }
        return null;
    }
}
