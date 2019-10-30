package some.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import some.example.calculator.RpnCalculator;



/**
 * RpnCalculatorTest
 * 
 * Thanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10569751)
 */

public class ANewlyRpnCalculatorShouldTests {

    private RpnCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = new RpnCalculator();
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma nova calculadora deve ter zero em seu acumulador")
    public void HaveZeroInItsAccumulator() {
        assertEquals(BigDecimal.ZERO, calculator.getAccumulator(), "the accumulator has to be zero");
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma nova calculadora deve permitir que seu acumulador seja definido")
    public void AllowItsAccumulatorToBeSet() {
        BigDecimal value = new BigDecimal("42");
        calculator.setAccumulator(value);
        assertEquals(value, calculator.getAccumulator());
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma nova calculadora não deve lançar uma exceção quando o método de remoção de valores do acumulador for executado")
    public void NotThrowAnExceptionWhenDropCalled() {
        calculator.drop();
    }
}