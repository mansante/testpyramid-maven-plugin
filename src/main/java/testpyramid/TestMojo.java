package testpyramid;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.File;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import testpyramid.pyramid.PyramidTestExecutionListener;


@Mojo(name = "test",
      requiresDependencyResolution = ResolutionScope.RUNTIME,
      requiresProject = true,
      threadSafe = true)
public class TestMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

  @Parameter(defaultValue = "${plugin}", readonly = true, required = true)
  private PluginDescriptor plugin;

  @Parameter(defaultValue = "false", readonly = true, required = false)
  private Boolean showFlatSummary;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    getLog().info("Running tests of the " + this.project.getName());

    List<String> projectElements = getProjectTestClasspathElements(this.project);
    addProjectElementsIntoClassLoader(projectElements, this.plugin);

    if (getLog().isDebugEnabled()) {
      showClassLoader();
    }

    PyramidTestExecutionListener pyramidListener = new PyramidTestExecutionListener(getLog());
    SummaryGeneratingListener listener = new SummaryGeneratingListener();

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(selectPackage(this.project.getGroupId()))
        .filters(includeClassNamePatterns(ClassNameFilter.STANDARD_INCLUDE_PATTERN))
        .build();

    Launcher launcher = LauncherFactory.create();
    launcher.discover(request);
    launcher.registerTestExecutionListeners(listener, pyramidListener);
    launcher.execute(request);

    if (showFlatSummary) {
      TestExecutionSummary summary = listener.getSummary();
      summary.printTo(new PrintWriter(System.out));
    }
  }

  private void addProjectElementsIntoClassLoader(List<String> elements, PluginDescriptor plugin) {
    ClassRealm classRealm = plugin.getClassRealm();
    for (String element : elements) {
      try {
        classRealm.addURL((new File(element)).toURI().toURL());
      } catch (MalformedURLException e) {
        getLog().error("When creating project classloader", e);
      }
    }
  }

  private List<String> getProjectTestClasspathElements(MavenProject project) {
    List<String> testClasspathElements = null;
    try {
      testClasspathElements = project.getTestClasspathElements();
    } catch (DependencyResolutionRequiredException e) {
      getLog().error("Dependency resolution failed", e);
    }
    return testClasspathElements;
  }

  private void showClassLoader() {
    getLog().debug("");
    getLog().debug("---------------------------------------");
    getLog().debug("context class loader hierarchy: "
        + ClassLoaderUtils.showClassLoaderHierarchy(
            Thread.currentThread().getContextClassLoader()));
    getLog().debug("");
    getLog().debug("plugin class loader hierarchy: "
        + ClassLoaderUtils.showClassLoaderHierarchy(
            TestMojo.class.getClassLoader()));
    getLog().debug(" ---------------------------------------");
    getLog().debug("");
  }

}