package testpyramid.pyramid;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestExecutionResult.Status;
import org.junit.platform.engine.TestTag;
import org.junit.platform.launcher.TestIdentifier;

class PyramidItem {

  private final String id;
  private final String description;
  private final Instant creation;
  private String layer;
  private long duration;
  private String reason;
  private Status status;
  private String errorMessage;
  private TestIdentifier identifier;
  private TestExecutionResult result;

  PyramidItem(TestIdentifier identifier) {
    this.identifier = identifier;
    this.id = identifier.getUniqueId();
    this.layer = extractLayer(identifier);
    this.description = identifier.getDisplayName();
    this.creation = Instant.now();
  }

  PyramidItem(TestIdentifier identifier, String reason) {
    this(identifier);
    this.reason = extractReason(reason);
  }

  PyramidItem setResult(TestExecutionResult result) {
    this.result = result;
    this.status = result().get().getStatus();
    this.duration = Duration.between(creation, Instant.now()).toMillis();

    if (result.getThrowable().isPresent()) {
      this.errorMessage = result.getThrowable().get().getMessage();
    }

    return this;
  }

  String id() {
    return this.id;
  }

  String description() {
    return this.description;
  }

  String layer() {
    return this.layer;
  }

  long duration() {
    return this.duration;
  }

  Optional<String> reason() {
    return Optional.ofNullable(this.reason);
  }

  Optional<Status> status() {
    return Optional.ofNullable(this.status);
  }

  Optional<TestIdentifier> identifier() {
    return Optional.ofNullable(this.identifier);
  }

  Optional<String> errorMessage() {
    return Optional.ofNullable(this.errorMessage);
  }

  Optional<TestExecutionResult> result() {
    return Optional.ofNullable(this.result);
  }

  boolean isSuccessful() {
    boolean success = false;
    if (result().isPresent()) {
      success = Status.SUCCESSFUL.equals(status().get());
    }
    return success;
  }

  boolean isFailed() {
    boolean failed = false;
    if (result().isPresent()) {
      failed = Status.FAILED.equals(status().get());
    }
    return failed;
  }

  boolean isAborted() {
    boolean aborted = false;
    if (result().isPresent()) {
      aborted = Status.ABORTED.equals(status().get());
    }
    return aborted;
  }

  boolean isSkipped() {
    return reason().isPresent();
  }

  static String extractLayer(TestIdentifier identifier) {
    Set<TestTag> tags = identifier.getTags();
    if (!tags.isEmpty()) {
      return tags.iterator().next().getName();
    }
    return "NONE";
  }

  private String extractReason(String reason) {
    return reason.contains("is @Disabled") ? "not defined!" : reason;
  }
}