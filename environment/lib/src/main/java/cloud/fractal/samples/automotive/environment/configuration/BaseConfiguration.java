package cloud.fractal.samples.automotive.environment.configuration;

import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

public abstract class BaseConfiguration implements EnvironmentConfiguration {

  @Override
  public ResourceGroupId getProductionAudiResourceGroupId() {
    return ResourceGroupId.fromString(String.format("Personal/%s/production-audi", getFractalAccountId()));
  }

  @Override
  public ResourceGroupId getStagingAudiResourceGroupId() {
    return ResourceGroupId.fromString(String.format("Personal/%s/staging-audi", getFractalAccountId()));
  }

  @Override
  public ResourceGroupId getProductionToyotaResourceGroupId() {
    return ResourceGroupId.fromString(String.format("Personal/%s/production-toyota", getFractalAccountId()));
  }

  @Override
  public ResourceGroupId getStagingToyotaResourceGroupId() {
    return ResourceGroupId.fromString(String.format("Personal/%s/staging-toyota", getFractalAccountId()));
  }

  @Override
  public ResourceGroupId getProductionResourceGroupId() {
    return ResourceGroupId.fromString(String.format("Personal/%s/production", getFractalAccountId()));
  }

  @Override
  public ResourceGroupId getStagingResourceGroupId() {
    return ResourceGroupId.fromString(String.format("Personal/%s/staging", getFractalAccountId()));
  }
}
