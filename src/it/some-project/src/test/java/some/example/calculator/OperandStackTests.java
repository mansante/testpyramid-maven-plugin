package some.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import some.example.calculator.OperandStack;



/**
 * OperandStackTests
 * 
 * Thanks Brett L. Schuchert for TDD tutorial (https://vimeo.com/10569751)
 */
public class OperandStackTests {

    OperandStack values;

    @BeforeEach
    public void init() {
        values = new OperandStack();    
    }

    @Test
    @Tag("INTEGRACAO")
    @DisplayName("Uma pilha de operandos nunca deve está vazia")
    public void IsNeverEmpty() {
        assertEquals(BigDecimal.ZERO, values.peek());
    }

    @Test
    @Tag("INTEGRACAO")
    @DisplayName("Deve ser possível subistituir o valor do topo da pilha")
    public void CanReplaceTopValue() {
        values.replaceTop(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, values.peek());
    }

    @Test
    @Tag("INTEGRACAO")
    @DisplayName("Deve ser possivel incluir valores na pilha")
    public void CanHaveValuesPushed() {
        values.push(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, values.peek());
    }

    @Test
    @Tag("INTEGRACAO")
    @DisplayName("Deve ser possivel remover valores na pilha")
    public void TopValueRemovedOnPop() {
        values.replaceTop(new BigDecimal("19"));
        values.pop();
        assertEquals(BigDecimal.ZERO, values.peek());
    }

    @Test
    @Tag("INTEGRACAO")
    @DisplayName("Deve ser possivel executar o método de remoção mesmo com a pilha vazia")
    public void PopCanBeCalledWhenEmpty() {
        values.pop();
    }
}