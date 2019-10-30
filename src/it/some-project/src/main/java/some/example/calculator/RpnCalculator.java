package some.example.calculator;

import java.math.BigDecimal;

import some.example.calculator.operator.Add;
import some.example.calculator.operator.Factorial;
import some.example.calculator.operator.MathOperator;
import some.example.calculator.operator.NoSuchOperatorException;
import some.example.calculator.operator.Operator;
import some.example.calculator.operator.Subtract;

/**
 * Calculator
 * 
 * Thanks Brett L. Schuchert for TDD tutorial: https://vimeo.com/10569751
 * https://vimeo.com/10570537 https://vimeo.com/10597511
 * 
 * 
 */
public class RpnCalculator {

    private OperandStack values = new OperandStack();

    public BigDecimal getAccumulator() {
        return this.values.peek();
    }

    public void setAccumulator(BigDecimal value) {
        this.values.replaceTop(value);
    }

    public void enter() {
        this.values.push(this.getAccumulator());
    }

    public void drop() {
        this.values.pop();
    }

    public void execute(Operator operatorType) {
        MathOperator operator = findOperator(operatorType);
        operator.execute(values);
    }

    private MathOperator findOperator(Operator operatorType) {
        switch(operatorType) {
            case ADD: return new Add();
            case SUBTRACT: return new Subtract();
            case FACTORIAL: return new Factorial();
            default: throw new NoSuchOperatorException();
        }   
    }

}