package some.example.calculator;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * OperandStack
 * 
 * hanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10569751)
 */
public class OperandStack {

    private final Stack<BigDecimal> values = new Stack<BigDecimal>();

    public BigDecimal peek() {
        if (!this.values.empty()) {
            return this.values.peek();
        }
        return BigDecimal.ZERO;
    }

    public void replaceTop(BigDecimal value) {
        pop();
        this.values.push(value);
    }

    public void push(BigDecimal value) {
        this.values.push(value);
    }

    public void pop() {
        if (!this.values.empty()) {
            this.values.pop();
        }
    }

}