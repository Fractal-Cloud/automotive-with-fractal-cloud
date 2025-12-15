package cloud.fractal.samples.automotive.architecture.livesystems;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureNodePool;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.AzureEventhubInstance;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.AzureEventhubNamespace;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureMachineType.*;

class ContainerizedAzure extends ContainerizedAgnostic
  <AzureKubernetesService, AzureKubernetesService.AzureKubernetesServiceBuilder>
{
  static final AzureRegion REGION = AzureRegion.WEST_EUROPE;
  private final AzureResourceGroup resourceGroup;

  @Override
  protected String getStorageClassName() {
    return "managed-csi";
  }

  ContainerizedAzure(
    ResourceGroupId resourceGroupId,
    LiveSystemIdValue liveSystemId,
    String description)
  {
    super(resourceGroupId, liveSystemId, description);
    resourceGroup = AzureResourceGroup.builder()
      .withName(String.format("rg-%s", liveSystemId.name()))
      .withRegion(REGION)
      .build();
  }

  @Override
  public KubernetesCluster.Builder<AzureKubernetesService, AzureKubernetesService.AzureKubernetesServiceBuilder> getKubernetesClusterBuilder() {
    return AzureKubernetesService.builder()
      .withRegion(resourceGroup.getRegion())
      .withNodePools(getNodePools())
      .withResourceGroup(resourceGroup);
  }

  @Override
  protected LiveSystemComponent getStreamingComponent() {
    return AzureEventhubNamespace.builder()
      .withName(liveSystemId().name())
      .withId(ComponentId.from(liveSystemId().name()))
      .withDisplayName(liveSystemId().name())
      .withRegion(REGION)
      .withMaximumThroughputUnits(20)
      .withInstance(AzureEventhubInstance.builder()
        .withDisplayName(String.format("%s-eh", liveSystemId().name()))
        .withId(String.format("%s-eh", liveSystemId().name()))
        .build())
      .build();
  }

  private static Collection<? extends AzureNodePool> getNodePools() {
    return List.of(
      AzureNodePool.builder()
        .withName("linuxdynamic")
        .withMinNodeCount(1)
        .withMaxNodeCount(10)
        .withAutoscalingEnabled(true)
        .withMachineType(STANDARD_B8MS)
        .build(),
      AzureNodePool.builder()
        .withName("linuxdyn2")
        .withMinNodeCount(1)
        .withMaxNodeCount(10)
        .withAutoscalingEnabled(true)
        .withMachineType(STANDARD_D4_V2)
        .build()
    );
  }

}
