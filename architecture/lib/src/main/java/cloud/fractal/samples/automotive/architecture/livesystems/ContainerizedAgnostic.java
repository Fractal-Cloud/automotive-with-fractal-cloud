package cloud.fractal.samples.automotive.architecture.livesystems;

import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSAmbassador;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSElasticLogging;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSPrometheus;
import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import cloud.fractal.samples.automotive.environment.AutomotiveSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class ContainerizedAgnostic
  <KT extends KubernetesCluster, KB extends KubernetesCluster.Builder<KT, KB>> implements AutomotiveSystem
{
  private final LiveSystemIdValue liveSystemId;
  private final ResourceGroupId fractalResourceGroupId;
  private final String description;
  private final Collection<CaaSKubernetesWorkload> k8sWorkloads;

  protected abstract String getStorageClassName();

  public ContainerizedAgnostic(
    ResourceGroupId fractalResourceGroupId,
    LiveSystemIdValue liveSystemId,
    String description
  ) {
    this.liveSystemId = liveSystemId;
    this.fractalResourceGroupId = fractalResourceGroupId;
    this.description = description;
    k8sWorkloads = new ArrayList<>();
  }

  public String description() {
    return description;
  }

  public LiveSystemIdValue liveSystemId() {
    return liveSystemId;
  }

  public FractalIdValue fractalId() {
    return new FractalIdValue(
      fractalResourceGroupId,
      "containerized",
      "1.0");
  }

  public Collection<? extends LiveSystemComponent> components() {
    var k8sId = String.format("k8s-%s", liveSystemId.name());
    var ambassadorId =  ComponentId.from("ambassador");

    var k8sCluster = getKubernetesClusterBuilder()
      .withId(k8sId)
      .withDisplayName(k8sId)
      .withAPIGateway(getAmbassador(ambassadorId))
      .withMonitoring(getMonitoringSolution(ambassadorId))
      .withLogging(getLoggingSolution(ambassadorId))
      .withK8sWorkloadInstances(k8sWorkloads)
      .build();

    var streamingComponent = getStreamingComponent();
    if (streamingComponent != null) {
      return List.of(k8sCluster, streamingComponent);
    } else {
      return List.of(k8sCluster);
    }
  }

  public void withK8sWorkloads(Collection<? extends CaaSKubernetesWorkload> workloads) {
    k8sWorkloads.addAll(workloads);
  }

  public void withK8sWorkload(CaaSKubernetesWorkload workload) {
    withK8sWorkloads(List.of(workload));
  }

  protected abstract KubernetesCluster.Builder<KT, KB> getKubernetesClusterBuilder();
  protected abstract LiveSystemComponent getStreamingComponent();

  private static CaaSAmbassador getAmbassador(ComponentId componentId) {
    return CaaSAmbassador.builder()
      .withId(componentId)
      .withDisplayName("ambassador")
      .withHostOwnerEmail("automotive@fractal.cloud")
      .withAcmeProviderAuthority("https://acme-v02.api.letsencrypt.org/directory")
      .withTlsSecretName("env-tls-cert")
      .withNamespace("ambassador-01")
      .build();
  }

  private CaaSElasticLogging getLoggingSolution(ComponentId ambassadorComponentId) {
    return CaaSElasticLogging.builder()
      .withId("elastic-logging")
      .withDisplayName("elastic-logging")
      .withVersion("2.5.0")
      .withNamespace("elastic")
      .withKibana(true)
      .withAPM(true)
      .withElasticVersion("8.5.0")
      .withInstances(1)
      .withStorage("10Gi")
      .withStorageClassName(getStorageClassName())
      .withMemory(1)
      .withCpu(1)
      .withDependency(ambassadorComponentId)
      .build();
  }

  private CaaSPrometheus getMonitoringSolution(ComponentId ambassadorComponentId) {
    return CaaSPrometheus.builder()
      .withId("prometheus")
      .withDescription("Prometheus monitoring")
      .withDisplayName("prometheus")
      .withNamespace("monitoring")
      .withDependency(ambassadorComponentId)
      .build();
  }
}
