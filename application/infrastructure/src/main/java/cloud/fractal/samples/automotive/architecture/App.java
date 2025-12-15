package cloud.fractal.samples.automotive.architecture;

import cloud.fractal.samples.automotive.architecture.livesystems.AudiLiveSystem;
import cloud.fractal.samples.automotive.architecture.configuration.EnvVarConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class App {

  public static void main(String[] args) throws IOException, InstantiatorException {
    var configuration = new EnvVarConfiguration();
    var liveSystemsService = new AudiLiveSystem(configuration.getEnvironment());
    liveSystemsService.withK8sWorkloads(getK8sWorkloads());
    liveSystemsService.deploy();
  }

  private static Collection<? extends CaaSKubernetesWorkload> getK8sWorkloads() {
    var namespace = "app";

    var repoId = "automotive-with-fractal-cloud";
    var repositoryUri = String.format("git@github.com:Fractal-Cloud/%s.git", repoId);

    var branchName = "main";

    return List.of(
      CaaSKubernetesWorkload.builder()
        .withId("app-reader")
        .withDisplayName("app-reader")
        .withDescription("App Reader")
        .withSSHRepositoryURI(repositoryUri)
        .withRepoId(repoId)
        .withBranchName(branchName)
        .withNamespace(namespace)
        .build(),
      CaaSKubernetesWorkload.builder()
        .withId("app-writer")
        .withDisplayName("app-writer")
        .withDescription("App Writer")
        .withSSHRepositoryURI(repositoryUri)
        .withRepoId(repoId)
        .withBranchName(branchName)
        .withNamespace(namespace)
        .build()
    );
  }
}
