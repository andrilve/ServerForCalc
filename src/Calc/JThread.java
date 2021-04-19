package Calc;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

public class JThread extends Thread {

    private final HashMap<String, Function<Double[], Double>> mathOperator;
    public Queue<String> queue;
    private Double out;
    private Thread current;

    private String equalSign = "=";
    private String squareSign = "^2";
    private String openBrace = "(";
    private String closeBrace = ")";

    Function<Double[], Double> plus = MathCalc::plus;
    Function<Double[], Double> minus = MathCalc::minus;
    Function<Double[], Double> multiplying = MathCalc::multiplying;
    Function<Double[], Double> division = MathCalc::divisionTrue;
    Function<Double[], Double> square = MathCalc::square;

    public JThread( Queue<String> queue ) {
        this.queue = queue;
        HashMap<String, Function<Double[], Double>> mathOperators = new HashMap<>();
        this.mathOperator = mathOperators;

        mathOperators.put("+", plus);
        mathOperators.put("-", minus);
        mathOperators.put("*", multiplying);
        mathOperators.put("/", division);
        mathOperators.put(squareSign, square);
    }

    public void run() {

        this.current = Thread.currentThread();

        HashMap<String, Integer> signH = new HashMap<>();
        signH.put(closeBrace, 5);
        signH.put(equalSign, 6);
        signH.put("(", 4);
        signH.put(squareSign, 3);
        signH.put("*", 2);
        signH.put("/", 2);
        signH.put("+", 1);
        signH.put("-", 1);

        //1st wile - if algoritm over to restart
        while (true) {
            if (current.isInterrupted()) {
                System.out.println("Работа потока была прервана");
                break;
            }
            else{
                try {
                    out = doAlgoritm(queue, signH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Double doAlgoritm( Queue<String> queue, HashMap<String, Integer> signH) throws InterruptedException {

        Stack<Double> numStack = new Stack<>();
        Stack<String> stackSigne = new Stack<>();

        int size = 0;
        Integer priority;

        /*2nd while - do until = */
        while (true) {
            Thread.sleep(2000);
            while (queue.size() > 0) {
                //System.out.println("может ты один раз попала и больше попадать не хочешь?");
                String value = queue.poll();
                priority = signH.get(value);
                if (priority != null) {
                    /* the sign stack is empty, just put first sign to it */
                    if (stackSigne.size() == 0 && !value.equals(squareSign) && !value.equals(openBrace)) {
                        stackSigne.push(value);
                    }
                    /* we start calculating of value in brackets separately*/
                    else if (priority == signH.get(openBrace)) {
                        numStack.push(doAlgoritm(queue, signH));
                    }
                    /* the end of expression, the result should be provided */
                    else if (priority == signH.get(closeBrace) || priority == signH.get(equalSign)) {
                        calculate(numStack, stackSigne);
                        return numStack.pop();
                    }
                    else if(value.equals(squareSign)){
                        calculateSquare( numStack);
                    }
                    /* the new operator have higher priority, put it to stack */
                    else if (priority > signH.get(stackSigne.peek())) {
                        stackSigne.push(value);
                    }
                    /* the previous operator has higher priority and can be executed as other operators with same priority*/
                    else {
                        while (numStack.size() > 1 && priority <= signH.get(stackSigne.peek())) {
                            Double[] operands = new Double[2];
                            operands[1] = numStack.pop();
                            operands[0] = numStack.pop();
                            String sign = stackSigne.pop();
                            out = mathOperator.get(sign).apply(operands);
                            numStack.push(out);
                            OutputStream otpravit = new OutputStream();
                            otpravit.otpravitDannuu(out);
                        }
                        stackSigne.push(value);
                    }
                } else {
                    numStack.push(Double.parseDouble(value));
                }
            }
        }
    }

    private void calculate( Stack<Double> numStack, Stack<String> signStack ) {
        while (numStack.size() > 1) {
            Double[] operands = new Double[2];
            operands[1] = numStack.pop();
            operands[0] = numStack.pop();
            String sign = signStack.pop();
            out = mathOperator.get(sign).apply(operands);
            //out = execute(firstValue, secondValue, sign);
            numStack.push(out);
            OutputStream otpravit = new OutputStream();
            otpravit.otpravitDannuu(out);
        }
    }

    private void calculateSquare( Stack<Double> numStack) {
        Double[] operands = new Double[1];
        operands[0] = numStack.pop();
        out = mathOperator.get(squareSign).apply(operands);
        numStack.push(out);
        OutputStream otpravit = new OutputStream();
        otpravit.otpravitDannuu(out);
    }
}
