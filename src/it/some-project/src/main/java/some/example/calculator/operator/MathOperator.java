package some.example.calculator.operator;

import some.example.calculator.OperandStack;

public interface MathOperator {

    public void execute(OperandStack values);
}