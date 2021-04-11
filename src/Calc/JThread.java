package Calc;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

public class JThread extends Thread {

    private final HashMap<String, Function<Double[][] , Double>> mathOperator;
    private final Queue queue;
    private Stack stackMathOp;
    private Stack stackNumb;
    private Double out;

    public JThread(String name, Queue queue, HashMap mathOperators, Stack stackMathOp, Stack stackNumb, Double out){
        super(name);
        this.queue = queue;
        this.mathOperator = mathOperators;
        this.stackMathOp = stackMathOp;
        this.stackNumb = stackNumb;
        this.out = out;
    }

    public void run(){
        while (true){
            if (queue.size() > 0){
                if (mathOperator.containsKey(queue.element())){
                    stackMathOp.push(queue.element());
                }
                else {
                    stackNumb.push(queue.element());
                    while (stackNumb.size() > 1){
                        double secondValue = (double) stackNumb.pop();
                        double firstValue = (double) stackNumb.pop();
                        String mathOp = (String) stackMathOp.pop();
                        if ( mathOp == "+"){
                            Double[][] twoDimArray = new Double[1][1];
                            System.out.println(mathOperator.get("+").apply(twoDimArray));
                            //stackNumb.push(MathCalcMoi.plus(firstValue, secondValue));
                        }
                    }

                }
            }
        }
    }
}
