package cloud.fractal.samples.automotive.architecture;

import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import cloud.fractal.samples.automotive.architecture.configuration.EnvVarConfiguration;
import cloud.fractal.samples.automotive.architecture.configuration.Environment;
import cloud.fractal.samples.automotive.architecture.livesystems.ContainerizedGcp;
import cloud.fractal.samples.automotive.architecture.livesystems.ContainerizedAzure;
import cloud.fractal.samples.automotive.environment.Environments;
import cloud.fractal.samples.automotive.environment.AutomotiveSystem;

import java.util.List;

import static cloud.fractal.samples.automotive.environment.Constants.*;

public class App {

  public static void main(String[] args) throws InstantiatorException {
    Environments environmentsService = new Environments();
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
        PRODUCTION_RESOURCE_GROUP_ID,
        new LiveSystemIdValue(PRODUCTION_TOYOTA_RESOURCE_GROUP_ID, "toyota-production"),
        "Production containerized workloads"));
  }

  private static List<AutomotiveSystem> getAudiProductionContainerizedWorkloads() {
    return List.of(
      new ContainerizedAzure(
        PRODUCTION_RESOURCE_GROUP_ID,
        new LiveSystemIdValue(PRODUCTION_AUDI_RESOURCE_GROUP_ID, "audi-production"),
        "Production containerized workloads"));
  }

  private static List<AutomotiveSystem> getToyotaStagingContainerizedWorkloads() {
    return List.of(
      new ContainerizedGcp(
        STAGING_RESOURCE_GROUP_ID,
        new LiveSystemIdValue(STAGING_TOYOTA_RESOURCE_GROUP_ID, "toyota-staging"),
        "Staging containerized workloads"));
  }

  private static List<AutomotiveSystem> getAudiStagingContainerizedWorkloads() {
    return List.of(
      new ContainerizedAzure(
        STAGING_RESOURCE_GROUP_ID,
        new LiveSystemIdValue(STAGING_AUDI_RESOURCE_GROUP_ID, "audi-staging"),
        "Staging containerized workloads"));
  }

  private static List<LiveSystemIdValue> getIds(List<AutomotiveSystem> liveSystems) {
    return liveSystems.stream().map(AutomotiveSystem::liveSystemId).toList();
  }
}
