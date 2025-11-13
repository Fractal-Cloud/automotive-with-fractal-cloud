package cloud.fractal.samples.automotive.environment;

import com.yanchware.fractal.sdk.Automaton;
import com.yanchware.fractal.sdk.domain.environment.*;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.List;
import java.util.UUID;

import static cloud.fractal.samples.automotive.environment.Constants.*;

public class Environments {

  private final ManagementEnvironment.ManagementEnvironmentBuilder staging;
  private final ManagementEnvironment.ManagementEnvironmentBuilder production;
  private final Automaton automaton;

  public Environments() {
    production = getCCoEManagementEnvironment(
      "Automotive - Production",
      "automotive-production",
      PRODUCTION_RESOURCE_GROUP_ID,
      PRODUCTION_AZURE_SUBSCRIPTION_ID,
      PRODUCTION_GCP_PROJECT_ID);
    staging = getCCoEManagementEnvironment(
      "Automotive - Staging",
      "automotive-staging",
      STAGING_RESOURCE_GROUP_ID,
      STAGING_AZURE_SUBSCRIPTION_ID,
      STAGING_GCP_PROJECT_ID);
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
      .withResourceGroup(PRODUCTION_AUDI_RESOURCE_GROUP_ID)
      .withShortName("audi-production")
      .withAzureSubscription(AZURE_REGION, PRODUCTION_AUDI_AZURE_SUBSCRIPTION_ID);

    return getAutomotiveEnvironment(production, ciCdProfile, secrets, opEnvBuilder);
  }

  public AutomotiveEnvironment stagingAudi() {
    return stagingAudi(null, null);
  }
  public AutomotiveEnvironment stagingAudi(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Audi - Staging")
      .withResourceGroup(STAGING_AUDI_RESOURCE_GROUP_ID)
      .withShortName("audi-staging")
      .withAzureSubscription(AZURE_REGION, STAGING_AUDI_AZURE_SUBSCRIPTION_ID);

    return getAutomotiveEnvironment(staging, ciCdProfile, secrets, opEnvBuilder);
  }

  public AutomotiveEnvironment productionToyota() {
    return productionToyota(null, null);
  }
  public AutomotiveEnvironment productionToyota(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Toyota - Production")
      .withResourceGroup(PRODUCTION_TOYOTA_RESOURCE_GROUP_ID)
      .withShortName("toyota-production")
      .withGcpProject(GCP_REGION, PRODUCTION_TOYOTA_GCP_PROJECT_ID);

    return getAutomotiveEnvironment(production, ciCdProfile, secrets, opEnvBuilder);
  }

  public AutomotiveEnvironment stagingToyota() {
    return stagingToyota(null, null);
  }
  public AutomotiveEnvironment stagingToyota(CiCdProfile ciCdProfile, List<Secret> secrets) {
    var opEnvBuilder = OperationalEnvironment.builder()
      .withName("Toyota - Staging")
      .withResourceGroup(STAGING_TOYOTA_RESOURCE_GROUP_ID)
      .withShortName("toyota-staging")
      .withGcpProject(GCP_REGION, STAGING_TOYOTA_GCP_PROJECT_ID);

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
        FRACTAL_ACCOUNT_ID,
        shortName
      ))
      .withName(displayName)
      .withResourceGroup(resourceGroupId)
      .withAzureCloudAgent(
        AZURE_REGION,
        AZURE_TENANT_ID,
        subscriptionId)
      .withGcpCloudAgent(
        GCP_REGION,
        GCP_ORGANIZATION_ID,
        projectId);
  }
}
