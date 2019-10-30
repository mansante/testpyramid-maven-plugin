package some.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import some.example.calculator.RpnCalculator;
import some.example.calculator.operator.NoSuchOperatorException;
import some.example.calculator.operator.Operator;


/**
 * RpnCalculatorRegisteredOperatorsTests
 * 
 * Thanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10597511)
 */
public class RpnCalculatorRegisteredOperatorsTests {

    private RpnCalculator calculator;
    BigDecimal value = new BigDecimal("42");
    BigDecimal value2 = new BigDecimal("4");

    @BeforeEach
    public void init() {
        calculator = new RpnCalculator();
        calculator.setAccumulator(this.value);
        calculator.enter();
        calculator.setAccumulator(this.value2);
    }

    @Test
    @Tag("COMPONENTE")
    @DisplayName("Dado que o operador de soma foi registrado a calculadora deve ser capaz de somar")
    public void ShouldBeAbleToAdd() {
        calculator.execute(Operator.ADD);
        assertEquals(new BigDecimal("46"), calculator.getAccumulator());
    }

    @Test
    @Tag("COMPONENTE")
    @DisplayName("Dado que o operador de subtrair foi registrado a calculadora deve ser capaz de subtrair")
    public void ShouldBeAbleToSubtract() {
        calculator.execute(Operator.SUBTRACT);
        assertEquals(new BigDecimal("38"), calculator.getAccumulator());
    }

    @Test
    @Tag("COMPONENTE")
    @DisplayName("Dado que o operador de fatorial foi registrado a calculadora deve ser capaz de calcular o fatorial")
    public void ShouldBeAbleToFactorial() {
        calculator.execute(Operator.FACTORIAL);
        assertEquals(new BigDecimal("24"), calculator.getAccumulator());
    }

    @Test 
    @Tag("COMPONENTE")
    @DisplayName("Se um operador não conhecido for registrado, uma exceção deve ser lançada")
    public void ShouldThrowExceptionForUnknowOperator() {
        assertThrows(NoSuchOperatorException.class, () -> {
            calculator.execute(Operator.ANYTHING);
        });
        
    }
}