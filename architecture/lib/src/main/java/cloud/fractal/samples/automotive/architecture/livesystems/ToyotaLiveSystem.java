package cloud.fractal.samples.automotive.architecture.livesystems;

import cloud.fractal.samples.automotive.architecture.Environment;
import cloud.fractal.samples.automotive.environment.Environments;
import cloud.fractal.samples.automotive.environment.configuration.EmbeddedResourceConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ToyotaLiveSystem {
  private final Environments environmentsService;
  private final ContainerizedAzure liveSystem;
  private final Environment environment;

  public ToyotaLiveSystem(Environment environment) throws IOException {
    this.environment = environment;
    environmentsService = new Environments();
    var environmentConfiguration = new EmbeddedResourceConfiguration();
    liveSystem = new ContainerizedAzure(
      environment == Environment.STAGING
        ? environmentConfiguration.getStagingResourceGroupId()
        : environmentConfiguration.getProductionResourceGroupId(),
      new LiveSystemIdValue(environment == Environment.STAGING
        ? environmentConfiguration.getStagingToyotaResourceGroupId()
        : environmentConfiguration.getProductionToyotaResourceGroupId(),
        String.format("toyota-%s", environment.name().toLowerCase())),
      String.format("%s containerized workloads", environment.name()));
  }

  public void withK8sWorkloads(Collection<? extends CaaSKubernetesWorkload> workloads) {
    liveSystem.withK8sWorkloads(workloads);
  }

  public void withK8sWorkload(CaaSKubernetesWorkload workload) {
    liveSystem.withK8sWorkload(workload);
  }

  public void deploy() throws InstantiatorException {
    if (environment.equals(Environment.STAGING)) {
      environmentsService.stagingToyota().deploy(List.of(liveSystem));
    } else {
      environmentsService.productionToyota().deploy(List.of(liveSystem));
    }
  }
}
