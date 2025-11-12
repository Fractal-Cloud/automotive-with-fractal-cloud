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

import java.util.Collection;
import java.util.List;

public abstract class ContainerizedAgnostic
  <KT extends KubernetesCluster, KB extends KubernetesCluster.Builder<KT, KB>> implements AutomotiveSystem
{
  private final LiveSystemIdValue liveSystemId;
  private final ResourceGroupId fractalResourceGroupId;
  private final String description;

  protected abstract String getStorageClassName();

  public ContainerizedAgnostic(
    ResourceGroupId fractalResourceGroupId,
    LiveSystemIdValue liveSystemId,
    String description
  ) {
    this.liveSystemId = liveSystemId;
    this.fractalResourceGroupId = fractalResourceGroupId;
    this.description = description;
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
      .withK8sWorkloadInstances(getK8sWorkloads())
      .build();

    return List.of(k8sCluster);
  }

  protected abstract KubernetesCluster.Builder<KT, KB> getKubernetesClusterBuilder();

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

  private Collection<? extends CaaSKubernetesWorkload> getK8sWorkloads() {
    var namespace = "app";

    var repoId = "automotive-with-fractal-cloud";
    var repositoryUri = String.format("git@github.com:Fractal-Cloud/%s.git", repoId);

    var liveSystemName = liveSystemId.name();
    var branchName = "main";

    return List.of(
      CaaSKubernetesWorkload.builder()
        .withId("app-reader")
        .withDisplayName("app-reader")
        .withDescription(String.format("App Reader %s", liveSystemName))
        .withSSHRepositoryURI(repositoryUri)
        .withRepoId(repoId)
        .withBranchName(branchName)
        .withNamespace(namespace)
        .build(),
      CaaSKubernetesWorkload.builder()
        .withId("app-writer")
        .withDisplayName("app-writer")
        .withDescription(String.format("App Writer %s", liveSystemName))
        .withSSHRepositoryURI(repositoryUri)
        .withRepoId(repoId)
        .withBranchName(branchName)
        .withNamespace(namespace)
        .build()
    );
  }
}
