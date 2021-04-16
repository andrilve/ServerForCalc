package Calc;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

public class JThread extends Thread {

    private final HashMap<String, Function<Double[][] , Double>> mathOperator;
    private final Stack<String> stackMathOp;
    private final Stack<String> stackNumb;

    public Queue<String> queue;
    private Double out;

    public JThread(String name, Queue<String> queue, HashMap<String, Function<Double[][] , Double>> mathOperators, Stack<String> stackMathOp, Stack<String> stackNumb, Double out){
        super(name);
        this.queue = queue;
        this.mathOperator = mathOperators;
        this.stackMathOp = stackMathOp;
        this.stackNumb = stackNumb;
        this.out = out;
    }

    public void run(){

        Thread current = Thread.currentThread();

        boolean flag = true;
        while (flag){
            if(!current.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Работа потока была прервана");
                    flag = false;
                    break;
                }
            }
           String elementQueue = queue.poll();
            if (elementQueue != null) {
                //System.out.println("очередь" + elementQueue);
                if (mathOperator.containsKey(elementQueue)) {
                    stackMathOp.push(elementQueue);
                    //System.out.println("знак+");
                } else {
                    stackNumb.push(elementQueue);
                    while (stackNumb.size() > 1) {
                        double secondValue = Double.parseDouble(stackNumb.pop());
                        double firstValue = Double.parseDouble(stackNumb.pop());
                        String mathOp = stackMathOp.pop();
                        if (mathOp.equals("+")) {
                            Double[][] twoDimArray = new Double[1][2];
                            twoDimArray[0][0] = firstValue;
                            twoDimArray[0][1] = secondValue;
                            out = mathOperator.get("+").apply(twoDimArray);
                            Out.otpravitDannuu(out);
                            stackNumb.push(String.valueOf(out));
                            //stackNumb.push(MathCalcMoi.plus(firstValue, secondValue));
                        }
                    }
                }
            }
        }
    }
}
