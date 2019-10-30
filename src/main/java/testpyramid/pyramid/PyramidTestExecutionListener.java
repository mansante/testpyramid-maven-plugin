package testpyramid.pyramid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.maven.plugin.logging.Log;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class PyramidTestExecutionListener implements TestExecutionListener {

  private PyramidPrinter pyramidPrinter;
  private final Map<String, PyramidItem> pyramidItemItems = new ConcurrentHashMap<>();
  private final Map<String, PyramidLayer> layers = new ConcurrentHashMap<>();

  public PyramidTestExecutionListener(Log log) {
    this.pyramidPrinter = new PyramidPrinter(log);
  }

  @Override
  public void testPlanExecutionStarted(TestPlan testPlan) {
    pyramidPrinter.printHeader(testPlan.countTestIdentifiers(TestIdentifier::isTest));
  }

  @Override
  public void testPlanExecutionFinished(TestPlan testPlan) {
    pyramidPrinter.printAll(layers);
    pyramidPrinter.printSummary(layers);
  }

  @Override
  public void dynamicTestRegistered(TestIdentifier identifier) {
    pyramidPrinter.print("Test %s (not supported display of dynamics tests)", 
        identifier.getDisplayName());
  }

  @Override
  public void executionSkipped(TestIdentifier identifier, String reason) {
    if (identifier.isTest()) {
      addIntoLayer(addPyramidItem(new PyramidItem(identifier, reason)));
    }
  }

  @Override
  public void executionStarted(TestIdentifier identifier) {
    if (identifier.isTest()) {
      addIntoLayer(addPyramidItem(new PyramidItem(identifier)));
    }
  }

  @Override
  public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
    if (identifier.isTest()) {
      PyramidItem pyramidItem = pyramidItemItems.get(identifier.getUniqueId());
      pyramidItem.setResult(result);
    }
  }

  @Override
  public void reportingEntryPublished(TestIdentifier identifier, ReportEntry entry) {
    pyramidPrinter.print("Test %s (not supported display of report entries)", 
        identifier.getDisplayName());
  }

  private PyramidItem addPyramidItem(PyramidItem pyramidItem) {
    pyramidItemItems.put(pyramidItem.id(), pyramidItem);
    return pyramidItem;
  }

  private void addIntoLayer(PyramidItem pyramidItem) {
    if (layers.containsKey(pyramidItem.layer())) {
      PyramidLayer layer = layers.get(pyramidItem.layer());
      layer.addItem(pyramidItem);
    } else {
      layers.put(pyramidItem.layer(), new PyramidLayer(pyramidItem));
    }
  }
}