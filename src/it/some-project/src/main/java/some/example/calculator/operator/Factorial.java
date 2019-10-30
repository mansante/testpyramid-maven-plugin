package some.example.calculator.operator;

import java.math.BigDecimal;

import some.example.calculator.OperandStack;

/**
 * Factorial
 */
public class Factorial implements MathOperator {

    @Override
    public void execute(OperandStack values) {

        BigDecimal operand = values.peek();
        BigDecimal result = BigDecimal.ONE;

        while (operand.compareTo(BigDecimal.ONE) > 0) {
            result = result.multiply(operand);
            operand = operand.subtract(BigDecimal.ONE);
        }

        values.replaceTop(result);

    }

}