package cloud.fractal.samples.automotive.environment.configuration;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.UUID;

public interface EnvironmentConfiguration {
  UUID getFractalAccountId();
  AzureRegion getAzureRegion();
  UUID getAzureTenantId();
  UUID getProductionAzureSubscriptionId();
  UUID getStagingAzureSubscriptionId();
  UUID getProductionAudiAzureSubscriptionId();
  UUID getStagingAudiAzureSubscriptionId();

  GcpRegion getGcpRegion();
  String getGcpOrganizationId();
  String getProductionGcpProjectId();
  String getStagingGcpProjectId();
  String getProductionToyotaGcpProjectId();
  String getStagingToyotaGcpProjectId();

  ResourceGroupId getProductionAudiResourceGroupId();
  ResourceGroupId getStagingAudiResourceGroupId();
  ResourceGroupId getProductionToyotaResourceGroupId();
  ResourceGroupId getStagingToyotaResourceGroupId();
  ResourceGroupId getStagingResourceGroupId();
  ResourceGroupId getProductionResourceGroupId();
}
