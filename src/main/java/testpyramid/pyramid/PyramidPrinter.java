package testpyramid.pyramid;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.maven.plugin.logging.Log;

class PyramidPrinter {

  static final String INDENTATION_LEVEL_ONE = "   ";
  static final String INDENTATION_LEVEL_TWO = "      ";
  private final Log log;

  PyramidPrinter(Log log) {
    this.log = log;
  }

  void printHeader(long testsCount) {
    log.info("------------------------------------------------------------------------");
    log.info(formatLine(" ","P Y R A M I D (%s tests found)", testsCount));
    log.info("------------------------------------------------------------------------");
  }

  void printSummary(Map<String, PyramidLayer> layers) {
    log.info("------------------------------------------------------------------------");
    log.info(formatLine(" ", "P Y R A M I D (tests results)"));
    log.info("------------------------------------------------------------------------");

    for (String layerKey : layers.keySet()) {
      PyramidLayer layer = layers.get(layerKey);
      print("%s:", layer.layer());

      int run = layer.pyramidItems().size();
      long failures = layer.countPyramidItems(PyramidItem::isFailed);
      long skipped = layer.countPyramidItems(PyramidItem::isSkipped);

      printResult("Run: %d, Failures: %d, Skipped: %d", run, failures, skipped);
      blankLine();
    }
  }

  void printAll(Map<String, PyramidLayer> layers)  {
    for (String layerKey : layers.keySet()) {
      
      PyramidLayer layer = layers.get(layerKey);
      for (PyramidItem item : layer.pyramidItems()) {

        print("Test: %s", wrapping(item.description(), 90));

        if (item.isSkipped()) {
          printResult("Status: %s, Layer: %s", "SKIPPED", item.layer());
          printResult("Reason: %s", item.reason().get());
        } else {
          printResult("Status: %s, Layer: %s, Time elapsed: %s", 
                item.status().get(), item.layer(), formatMillis(item.duration()));
          if (item.isFailed()) {
            printResult("Error: %s", item.errorMessage().get());
          }
        }

        blankLine();
      }
    }
  }

  void print(String text, Object... args) {
    log.info(formatLine(INDENTATION_LEVEL_ONE, text, args));
  }

  private void printResult(String text, Object... args) {
    log.info(formatLine(INDENTATION_LEVEL_TWO, text, args));
  }

  private String formatLine(String tab, String text, Object... args) {
    StringBuilder line = new StringBuilder(tab);
    line.append(String.format(text, args)); 
    return line.toString();
  }

  private void blankLine() {
    log.info("");
  }

  static String wrapping(String text, int len) {
    boolean normal = text.length() <= len;
    String display = normal ? text : text.substring(0, len) + "...";
    return display;
  }

  // TODO: Revisar formatação de hora, minuto e segunto
  static String formatMillis(long timeInMillis) {
    String sign = "";
    if (timeInMillis < 0) {
      sign = "-";
      timeInMillis = Math.abs(timeInMillis);
    }

    long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
    long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
    long millis = timeInMillis % TimeUnit.SECONDS.toMillis(1);

    final StringBuilder formatted = new StringBuilder(20);
    formatted.append(sign);
    formatted.append(String.format("%02d", minutes));
    formatted.append(String.format(":%02d", seconds));
    formatted.append(String.format(".%03d", millis));

    return formatted.toString();
  }

}