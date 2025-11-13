package cloud.fractal.samples.automotive.environment;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.UUID;

public class Constants {
  /* TODO: Configure */

  // Fractal Cloud Configuration
  protected static final UUID FRACTAL_ACCOUNT_ID = UUID.fromString("xxx");

  // Azure Configuration
  protected static final AzureRegion AZURE_REGION = AzureRegion.WEST_EUROPE;
  protected static final UUID AZURE_TENANT_ID = UUID.fromString("xxx");
  protected static final UUID PRODUCTION_AZURE_SUBSCRIPTION_ID = UUID.fromString("xxx");
  protected static final UUID STAGING_AZURE_SUBSCRIPTION_ID = UUID.fromString("xxx");
  protected static final UUID PRODUCTION_AUDI_AZURE_SUBSCRIPTION_ID = UUID.fromString("xx");
  protected static final UUID STAGING_AUDI_AZURE_SUBSCRIPTION_ID = UUID.fromString("xxx");

  // GCP Configuration
  protected static final GcpRegion GCP_REGION = GcpRegion.EUROPE_WEST3;
  protected static final String GCP_ORGANIZATION_ID = "xxx";
  protected static final String PRODUCTION_GCP_PROJECT_ID = "xxx";
  protected static final String STAGING_GCP_PROJECT_ID = "xxx";
  protected static final String PRODUCTION_TOYOTA_GCP_PROJECT_ID = "xxx";
  protected static final String STAGING_TOYOTA_GCP_PROJECT_ID = "xxx";
  /* End of TODO */

  public static final ResourceGroupId PRODUCTION_AUDI_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/production-audi", FRACTAL_ACCOUNT_ID));
  public static final ResourceGroupId STAGING_AUDI_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/staging-audi", FRACTAL_ACCOUNT_ID));
  public static final ResourceGroupId PRODUCTION_TOYOTA_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/production-toyota", FRACTAL_ACCOUNT_ID));
  public static final ResourceGroupId STAGING_TOYOTA_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/staging-toyota", FRACTAL_ACCOUNT_ID));
  public static final ResourceGroupId PRODUCTION_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/production", FRACTAL_ACCOUNT_ID));
  public static final ResourceGroupId STAGING_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/staging", FRACTAL_ACCOUNT_ID));
}
