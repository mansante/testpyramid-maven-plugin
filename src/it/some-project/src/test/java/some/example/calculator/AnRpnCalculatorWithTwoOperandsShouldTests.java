package some.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import some.example.calculator.RpnCalculator;
import some.example.calculator.operator.Operator;



/**
 * AnRpnCalculatorWithTwoOperandsShouldTests
 * 
 * Thanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10570537)
 */
public class AnRpnCalculatorWithTwoOperandsShouldTests {

    RpnCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = new RpnCalculator();
        calculator.setAccumulator(new BigDecimal("3"));
        calculator.enter();
        calculator.setAccumulator(new BigDecimal("4"));
    }

    @Test
    @Tag("COMPONENTE")
    @DisplayName("Uma calculadora com dois operandos deve somar dois números corretamente")
    public void AddTwoNumbersCorretly() {
        calculator.execute(Operator.ADD);
        assertEquals(new BigDecimal("7"), calculator.getAccumulator());
    }

    @Test
    @Tag("COMPONENTE")
    @DisplayName("Uma calculadora com dois operandos deve subtrair dois números corretamente")
    public void SubtractTwoNumbersCorretly() {
        calculator.execute(Operator.SUBTRACT);
        assertEquals(new BigDecimal("-1"), calculator.getAccumulator());
    }
}
