package cloud.fractal.samples.automotive.architecture;

import cloud.fractal.samples.automotive.environment.configuration.EmbeddedResourceConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import cloud.fractal.samples.automotive.architecture.configuration.EnvVarConfiguration;
import cloud.fractal.samples.automotive.architecture.configuration.Environment;
import cloud.fractal.samples.automotive.architecture.livesystems.ContainerizedGcp;
import cloud.fractal.samples.automotive.architecture.livesystems.ContainerizedAzure;
import cloud.fractal.samples.automotive.environment.Environments;
import cloud.fractal.samples.automotive.environment.AutomotiveSystem;

import java.io.IOException;
import java.util.List;

public class App {

  private static EmbeddedResourceConfiguration environmentConfiguration;

  public static void main(String[] args) throws InstantiatorException, IOException {
    Environments environmentsService = new Environments();
    environmentConfiguration = new EmbeddedResourceConfiguration();

    var configuration = new EnvVarConfiguration();
    var environment = configuration.getEnvironment();
    var deleteLiveSystems = args.length == 1 && args[0].equalsIgnoreCase("delete");

    if (environment == Environment.PRODUCTION) {
      if(deleteLiveSystems) {
        environmentsService.productionAudi().delete(getIds(getAudiProductionContainerizedWorkloads()));
        environmentsService.productionToyota().delete(getIds(getToyotaProductionContainerizedWorkloads()));
      } else {
        environmentsService.productionAudi().deploy(getAudiProductionContainerizedWorkloads());
        environmentsService.productionToyota().deploy(getToyotaProductionContainerizedWorkloads());
      }
    } else {
      if(deleteLiveSystems) {
        environmentsService.stagingAudi().delete(getIds(getAudiStagingContainerizedWorkloads()));
        environmentsService.stagingToyota().delete(getIds(getToyotaStagingContainerizedWorkloads()));
      } else {
        environmentsService.stagingAudi().deploy(getAudiStagingContainerizedWorkloads());
        environmentsService.stagingToyota().deploy(getToyotaStagingContainerizedWorkloads());
      }
    }
  }

  private static List<AutomotiveSystem> getToyotaProductionContainerizedWorkloads() {
    return List.of(
      new ContainerizedGcp(
        environmentConfiguration.getProductionResourceGroupId(),
        new LiveSystemIdValue(environmentConfiguration.getProductionToyotaResourceGroupId(), "toyota-production"),
        "Production containerized workloads"));
  }

  private static List<AutomotiveSystem> getAudiProductionContainerizedWorkloads() {
    return List.of(
      new ContainerizedAzure(
        environmentConfiguration.getProductionResourceGroupId(),
        new LiveSystemIdValue(environmentConfiguration.getProductionAudiResourceGroupId(), "audi-production"),
        "Production containerized workloads"));
  }

  private static List<AutomotiveSystem> getToyotaStagingContainerizedWorkloads() {
    return List.of(
      new ContainerizedGcp(
        environmentConfiguration.getStagingResourceGroupId(),
        new LiveSystemIdValue(environmentConfiguration.getStagingToyotaResourceGroupId(), "toyota-staging"),
        "Staging containerized workloads"));
  }

  private static List<AutomotiveSystem> getAudiStagingContainerizedWorkloads() {
    return List.of(
      new ContainerizedAzure(
        environmentConfiguration.getStagingResourceGroupId(),
        new LiveSystemIdValue(environmentConfiguration.getStagingAudiResourceGroupId(), "audi-staging"),
        "Staging containerized workloads"));
  }

  private static List<LiveSystemIdValue> getIds(List<AutomotiveSystem> liveSystems) {
    return liveSystems.stream().map(AutomotiveSystem::liveSystemId).toList();
  }
}
