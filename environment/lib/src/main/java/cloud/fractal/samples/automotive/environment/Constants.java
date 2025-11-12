package cloud.fractal.samples.automotive.environment;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.UUID;

public class Constants {
  protected static final AzureRegion AZURE_REGION = AzureRegion.WEST_EUROPE;
  protected static final UUID AZURE_TENANT_ID = UUID.fromString("82a60e0b-b00c-45e3-b4cb-74ee4799e0c1");
  protected static final UUID CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID = UUID.fromString("180085f7-536c-4883-aa66-41e68042b662");

  protected static final GcpRegion GCP_REGION = GcpRegion.EUROPE_WEST3;
  protected static final String GCP_ORGANIZATION_ID = "476716763173";

  protected static final UUID PRODUCTION_AZURE_SUBSCRIPTION_ID = UUID.fromString("91b7f870-d0e7-4a8f-8243-fdf90c380200");           // Fractal Demo - 1
  protected static final UUID STAGING_AZURE_SUBSCRIPTION_ID = UUID.fromString("ed100bf4-9875-4dc0-a18e-f2a18a69ec1e");              // Fractal Demo - 2
  protected static final UUID PRODUCTION_AUDI_AZURE_SUBSCRIPTION_ID = UUID.fromString("d084c40e-cfc1-4353-b71e-8bcb65f11173");      // Fractal Demo - 4
  protected static final UUID STAGING_AUDI_AZURE_SUBSCRIPTION_ID = UUID.fromString("399c779c-bc5f-47e3-98bc-3f649fea804e");         // Fractal Demo - 5

  protected static final String PRODUCTION_GCP_PROJECT_ID = "fractal-demo-0";
  protected static final String STAGING_GCP_PROJECT_ID = "fractal-demo-1";
  protected static final String PRODUCTION_TOYOTA_GCP_PROJECT_ID = "fractal-demo-2";
  protected static final String STAGING_TOYOTA_GCP_PROJECT_ID = "fractal-demo-3";

  public static final ResourceGroupId PRODUCTION_AUDI_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/production-audi", CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID));
  public static final ResourceGroupId STAGING_AUDI_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/staging-audi", CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID));
  public static final ResourceGroupId PRODUCTION_TOYOTA_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/production-toyota", CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID));
  public static final ResourceGroupId STAGING_TOYOTA_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/staging-toyota", CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID));
  public static final ResourceGroupId PRODUCTION_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/production", CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID));
  public static final ResourceGroupId STAGING_RESOURCE_GROUP_ID = ResourceGroupId.fromString(String.format("Personal/%s/staging", CLOUD_CENTER_OF_EXCELLENCE_ACCOUNT_ID));
}
