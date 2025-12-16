package cloud.fractal.samples.automotive.architecture;

import cloud.fractal.samples.automotive.architecture.livesystems.AudiLiveSystem;
import cloud.fractal.samples.automotive.architecture.configuration.EnvVarConfiguration;
import cloud.fractal.samples.automotive.architecture.livesystems.ReceiverLink;
import cloud.fractal.samples.automotive.architecture.livesystems.SenderLink;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class App {

  private static String liveSystemName;

  public static void main(String[] args) throws IOException, InstantiatorException {
    var configuration = new EnvVarConfiguration();
    var environment = configuration.getEnvironment();
    liveSystemName = String.format("audi-%s", environment.name().toLowerCase());
    var liveSystemsService = new AudiLiveSystem(environment, liveSystemName);
    liveSystemsService.withK8sWorkloads(getK8sWorkloads());
    liveSystemsService.deploy();
  }

  private static Collection<? extends CaaSKubernetesWorkload> getK8sWorkloads() {
    var namespace = "app";

    var repoId = "automotive-with-fractal-cloud";
    var repositoryUri = String.format("git@github.com:Fractal-Cloud/%s.git", repoId);

    return List.of(
      CaaSKubernetesWorkload.builder()
        .withId("app-reader")
        .withDisplayName("app-reader")
        .withDescription("App Reader")
        .withSSHRepositoryURI(repositoryUri)
        .withRepoId(repoId)
        .withNamespace(namespace)
        .withLink(new ReceiverLink(liveSystemName))
        .build(),
      CaaSKubernetesWorkload.builder()
        .withId("app-writer")
        .withDisplayName("app-writer")
        .withDescription("App Writer")
        .withSSHRepositoryURI(repositoryUri)
        .withRepoId(repoId)
        .withNamespace(namespace)
        .withLink(new SenderLink(liveSystemName))
        .build()
    );
  }
}
