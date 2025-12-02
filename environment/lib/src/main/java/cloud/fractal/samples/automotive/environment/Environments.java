package cloud.fractal.samples.automotive.environment;

import cloud.fractal.samples.automotive.environment.configuration.EnvironmentConfiguration;
import cloud.fractal.samples.automotive.environment.configuration.EmbeddedResourceConfiguration;
import com.yanchware.fractal.sdk.Automaton;
import com.yanchware.fractal.sdk.domain.environment.*;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Environments {

  private final ManagementEnvironment.ManagementEnvironmentBuilder staging;
  private final ManagementEnvironment.ManagementEnvironmentBuilder production;
  private final Automaton automaton;
  private final EnvironmentConfiguration configuration;

  public Environments() throws IOException {
    this.configuration = new EmbeddedResourceConfiguration();
    production = getCCoEManagementEnvironment(
      "Automotive - Production",
      "automotive-production",
      configuration.getProductionResourceGroupId(),
      configuration.getProductionAzureSubscriptionId(),
      configuration.getProductionGcpProjectId());
    staging = getCCoEManagementEnvironment(
      "Automotive - Staging",
      "automotive-staging",
      configuration.getStagingResourceGroupId(),
      configuration.getStagingAzureSubscriptionId(),
      configuration.getStagingGcpProjectId());
    try {
      automaton = Automaton.getInstance();
    } catch (InstantiatorException e) {
      throw new RuntimeException(e);
    }
  }

  public AutomotiveEnvironment productionAudi() {
    return productionAudi(null, null);
  }
  public AutomotiveEnvironment productionAudi(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Audi - Production")
      .withResourceGroup(configuration.getProductionAudiResourceGroupId())
      .withShortName("audi-production")
      .withAzureSubscription(configuration.getAzureRegion(), configuration.getProductionAudiAzureSubscriptionId());

    return getAutomotiveEnvironment(production, ciCdProfile, secrets, opEnvBuilder);
  }

  public AutomotiveEnvironment stagingAudi() {
    return stagingAudi(null, null);
  }
  public AutomotiveEnvironment stagingAudi(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Audi - Staging")
      .withResourceGroup(configuration.getStagingAudiResourceGroupId())
      .withShortName("audi-staging")
      .withAzureSubscription(configuration.getAzureRegion(), configuration.getStagingAudiAzureSubscriptionId());

    return getAutomotiveEnvironment(staging, ciCdProfile, secrets, opEnvBuilder);
  }

  public AutomotiveEnvironment productionToyota() {
    return productionToyota(null, null);
  }
  public AutomotiveEnvironment productionToyota(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Toyota - Production")
      .withResourceGroup(configuration.getProductionToyotaResourceGroupId())
      .withShortName("toyota-production")
      .withGcpProject(configuration.getGcpRegion(), configuration.getProductionToyotaGcpProjectId());

    return getAutomotiveEnvironment(production, ciCdProfile, secrets, opEnvBuilder);
  }

  public AutomotiveEnvironment stagingToyota() {
    return stagingToyota(null, null);
  }
  public AutomotiveEnvironment stagingToyota(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Toyota - Staging")
      .withResourceGroup(configuration.getStagingToyotaResourceGroupId())
      .withShortName("toyota-staging")
      .withGcpProject(configuration.getGcpRegion(), configuration.getStagingToyotaGcpProjectId());

    return getAutomotiveEnvironment(staging, ciCdProfile, secrets, opEnvBuilder);
  }

  private AutomotiveEnvironment getAutomotiveEnvironment(
    ManagementEnvironment.ManagementEnvironmentBuilder environmentBuilder,
    CiCdProfile ciCdProfile,
    List<Secret> secrets,
    OperationalEnvironment.OperationalEnvironmentBuilder opEnvBuilder)
  {
    if (ciCdProfile != null) {
      opEnvBuilder.withDefaultCiCdProfile(ciCdProfile);
    }

    if (secrets != null) {
      opEnvBuilder.withSecrets(secrets);
    }

    var environment = opEnvBuilder.build();

    return new AutomotiveEnvironment(
      automaton,
      automaton.getEnvironmentBuilder()
        .withManagementEnvironment(environmentBuilder.withOperationalEnvironment(environment)
          .build())
        .build(),
      environment.getId());
  }

  private ManagementEnvironment.ManagementEnvironmentBuilder getCCoEManagementEnvironment(
    String displayName,
    String shortName,
    ResourceGroupId resourceGroupId,
    UUID subscriptionId,
    String projectId)
  {
    return ManagementEnvironment.builder()
      .withId(new EnvironmentIdValue(
        EnvironmentType.PERSONAL,
        configuration.getFractalAccountId(),
        shortName
      ))
      .withName(displayName)
      .withResourceGroup(resourceGroupId)
      .withAzureCloudAgent(
        configuration.getAzureRegion(),
        configuration.getAzureTenantId(),
        subscriptionId)
      .withGcpCloudAgent(
        configuration.getGcpRegion(),
        configuration.getGcpOrganizationId(),
        projectId);
  }
}
