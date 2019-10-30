package testpyramid.pyramid;

import static java.util.Collections.unmodifiableSet;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import org.junit.platform.commons.util.Preconditions;

class PyramidLayer {

  private final String layer;
  private final Map<String, PyramidItem> pyramidItems = new ConcurrentHashMap<>();

  PyramidLayer(PyramidItem pyramidItem) {
    this.layer = pyramidItem.layer();
    pyramidItems.put(pyramidItem.id(), pyramidItem);
  }

  boolean addItem(PyramidItem pyramidItem) {
    if (layer.equals(pyramidItem.layer())) {
      pyramidItems.put(pyramidItem.id(), pyramidItem);
    } else {
      return false;
    }
    return true;
  }

  String layer() {
    return this.layer;
  }

  Set<PyramidItem> pyramidItems() {
    return unmodifiableSet(new HashSet<PyramidItem>(this.pyramidItems.values()));
  }

  Optional<PyramidItem> get(String id) {
    return Optional.ofNullable(pyramidItems.get(id));
  }

  public long countPyramidItems(Predicate<? super PyramidItem> predicate) {
    Preconditions.notNull(predicate, "Predicate must not be null");
    return this.pyramidItems.values().stream().filter(predicate).count();
  }

}