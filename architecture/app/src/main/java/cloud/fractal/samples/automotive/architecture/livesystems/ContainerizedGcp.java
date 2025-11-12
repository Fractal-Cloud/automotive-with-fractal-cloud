package cloud.fractal.samples.automotive.architecture.livesystems;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpMachine.E2_STANDARD4;

public class ContainerizedGcp extends ContainerizedAgnostic
  <GoogleKubernetesEngine, GoogleKubernetesEngine.GoogleKubernetesEngineBuilder>
{
  static final GcpRegion REGION = GcpRegion.EUROPE_WEST3;

  @Override
  protected String getStorageClassName() {
    return "premium-rwo";
  }

  public ContainerizedGcp(
    ResourceGroupId resourceGroupId,
    LiveSystemIdValue liveSystemId,
    String description)
  {
    super(resourceGroupId, liveSystemId, description);
  }

  @Override
  public KubernetesCluster.Builder<GoogleKubernetesEngine, GoogleKubernetesEngine.GoogleKubernetesEngineBuilder> getKubernetesClusterBuilder() {
    return GoogleKubernetesEngine.builder()
      .withNodePools(getNodePools())
      .withRegion(REGION);
  }

  private static Collection<? extends GcpNodePool> getNodePools() {
    return List.of(
      GcpNodePool.builder()
        .withName("nodes")
        .withMachineType(E2_STANDARD4)
        .withInitialNodeCount(3)
        .withMaxNodeCount(20)
        .build(),
      GcpNodePool.builder()
        .withName("nodes2")
        .withMachineType(E2_STANDARD4)
        .withInitialNodeCount(3)
        .withMaxNodeCount(20)
        .build()
    );
  }
}
