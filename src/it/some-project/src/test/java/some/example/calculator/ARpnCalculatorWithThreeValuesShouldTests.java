package some.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * ARpnCalculatorWithThreeValuesShouldTests
 * 
 * Thanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10569751)
 */
public class ARpnCalculatorWithThreeValuesShouldTests {

    private RpnCalculator calculator;
    BigDecimal value = new BigDecimal("42");
    BigDecimal value2 = new BigDecimal("2");
    BigDecimal value3 = new BigDecimal("3");

    @BeforeEach
    public void init() {
        calculator = new RpnCalculator();
        calculator.setAccumulator(this.value);
        calculator.enter();
        calculator.setAccumulator(this.value2);
        calculator.enter();
        calculator.setAccumulator(this.value3);
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma calculadora com três valores deve ter o último valor inserido em seu acumulador")
    public void HaveTheLastValueEnteredInItsAccumulator() {
        assertEquals(this.value3, calculator.getAccumulator());
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma calculadora com três valores deve ter o segundo ao último após a remoção de um valor do acumulador")
    public void HaveTheSecondToLastValueAfterASingleDrop() {
        calculator.drop();
        assertEquals(this.value2, calculator.getAccumulator());
    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma calculadora com três valores deve ter o primeiro valor depois de duas remoções")
    public void HaveTheFirstValueEnteredAfterTwoDrops() {
        calculator.drop();
        calculator.drop();
        assertEquals(this.value, calculator.getAccumulator());

    }

    @Test
    @Tag("UNITARIO")
    @DisplayName("Uma calculadora com três valores deve ter o valor zero no acumulador após três remoções")
    public void HaveZeroAfterThreeDrops() {
        calculator.drop();
        calculator.drop();
        calculator.drop();
        assertEquals(BigDecimal.ZERO, calculator.getAccumulator());

    }
}