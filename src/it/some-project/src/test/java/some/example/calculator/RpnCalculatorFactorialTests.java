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
 * RpnCalculatorFactorialTests
 * 
 * Thanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10570537)
 */
public class RpnCalculatorFactorialTests {

    private RpnCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = new RpnCalculator();
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("O resultado do calculo do factorial de zero deve ser um")
    public void FactorialOfZeroIsOne() {
        calculator.execute(Operator.FACTORIAL);
        assertEquals(BigDecimal.ONE, calculator.getAccumulator());
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("O resultado do calculo do factorial de cinco é cento e vinte")
    public void FactorialOfFiveIsOneHundredTwenty() {
        calculator.setAccumulator(new BigDecimal("5"));
        calculator.execute(Operator.FACTORIAL);
        assertEquals(new BigDecimal("120"), calculator.getAccumulator());
    }
}