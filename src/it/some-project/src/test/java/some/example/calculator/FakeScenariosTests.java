package some.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class FakeScenariosTests {

  @Test
  @DisplayName("Fake teste - camada nao definida no teste")
  public void fakeTestLayerNotDefined() {
    // fake code
  }

  @Test
  @Tag("INTEGRACAO")
  public void fakeTestDisplayNameNotDefined() {
    // fake code
  }

  @Test
  @Disabled("verificacao de teste desativado")
  @Tag("INTEGRACAO")
  @DisplayName("Fake teste - teste desativado")
  public void fakeTestSkipped() {
    // fake code
  }

  @Test
  @Disabled
  @Tag("INTEGRACAO")
  @DisplayName("Fake teste - teste desativado sem razão informada")
  public void fakeTestSkippedWithReasonNotDefined() {
    // fake code
  }

  @Test
  @Tag("INTEGRACAO")
  @DisplayName("Fake teste - teste com erro de execucao")
  public void fakeTestWithExecutionError() {
    // fake code
    int r = 1 / 0;
  }

  @Test
  @Tag("INTEGRACAO")
  @DisplayName("Fake teste - teste com falha")
  public void fakeTestWithFail() {
    assertEquals(BigDecimal.ONE, BigDecimal.TEN);
  }
}