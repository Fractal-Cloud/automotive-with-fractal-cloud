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

public class AudiLiveSystem {
  private final Environments environmentsService;
  private final ContainerizedAzure liveSystem;
  private final Environment environment;

  public AudiLiveSystem(Environment environment) throws IOException {
    this.environment = environment;
    environmentsService = new Environments();
    var environmentConfiguration = new EmbeddedResourceConfiguration();
    liveSystem = new ContainerizedAzure(
      environment == Environment.STAGING
        ? environmentConfiguration.getStagingResourceGroupId()
        : environmentConfiguration.getProductionResourceGroupId(),
      new LiveSystemIdValue(environment == Environment.STAGING
        ? environmentConfiguration.getStagingAudiResourceGroupId()
        : environmentConfiguration.getProductionAudiResourceGroupId(),
        String.format("audi-%s", environment.name().toLowerCase())),
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
      environmentsService.stagingAudi().deploy(List.of(liveSystem));
    } else {
      environmentsService.productionAudi().deploy(List.of(liveSystem));
    }
  }
}
