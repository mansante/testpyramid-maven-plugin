package some.example.calculator.operator;

import java.math.BigDecimal;

import some.example.calculator.OperandStack;

/**
 * Add
 */
public class Add implements MathOperator {

    @Override
    public void execute(OperandStack values) {
        BigDecimal rightHandSide = values.peek();
        values.pop();
        BigDecimal leftHandSide = values.peek();
        BigDecimal result = leftHandSide.add(rightHandSide);
        values.replaceTop(result);
    }
}