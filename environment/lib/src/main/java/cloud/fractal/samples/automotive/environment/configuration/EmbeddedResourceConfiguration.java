package cloud.fractal.samples.automotive.environment.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;

import java.io.IOException;
import java.util.UUID;

public class EmbeddedResourceConfiguration extends BaseConfiguration {
  private final JsonNode root;

  public EmbeddedResourceConfiguration() throws IOException {
    try (var is = getClass().getResourceAsStream("/configuration/tenancies.json"))
    {
      if (is == null) {
        throw new IllegalStateException("Embedded Resource not found: /configuration/tenancies.json");
      }

      ObjectMapper mapper = new ObjectMapper();
      root = mapper.readTree(is);
    }
  }

  @Override
  public UUID getFractalAccountId() {
    return UUID.fromString(root.get("fractalAccountId").asText());
  }

  @Override
  public AzureRegion getAzureRegion() {
    return AzureRegion.fromString(root.get("azureRegion").asText());
  }

  @Override
  public UUID getAzureTenantId() {
    return UUID.fromString(root.get("azureTenantId").asText());
  }

  @Override
  public UUID getProductionAzureSubscriptionId() {
    return UUID.fromString(root.get("productionAzureSubscriptionId").asText());
  }

  @Override
  public UUID getStagingAzureSubscriptionId() {
    return UUID.fromString(root.get("stagingAzureSubscriptionId").asText());
  }

  @Override
  public UUID getProductionAudiAzureSubscriptionId() {
    return UUID.fromString(root.get("productionAudiAzureSubscriptionId").asText());
  }

  @Override
  public UUID getStagingAudiAzureSubscriptionId() {
    return UUID.fromString(root.get("stagingAudiAzureSubscriptionId").asText());
  }

  @Override
  public GcpRegion getGcpRegion() {
    return GcpRegion.fromString(root.get("gcpRegion").asText());
  }

  @Override
  public String getGcpOrganizationId() {
    return root.get("gcpOrganizationId").asText();
  }

  @Override
  public String getProductionGcpProjectId() {
    return root.get("productionGcpProjectId").asText();
  }

  @Override
  public String getStagingGcpProjectId() {
    return root.get("stagingGcpProjectId").asText();
  }

  @Override
  public String getProductionToyotaGcpProjectId() {
    return root.get("productionToyotaGcpProjectId").asText();
  }

  @Override
  public String getStagingToyotaGcpProjectId() {
    return root.get("stagingToyotaGcpProjectId").asText();
  }
}
