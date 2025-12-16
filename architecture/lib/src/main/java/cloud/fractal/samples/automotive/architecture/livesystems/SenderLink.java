package cloud.fractal.samples.automotive.architecture.livesystems;

import com.yanchware.fractal.sdk.domain.ComponentLink;

public class SenderLink extends ComponentLink {
  public SenderLink(String liveSystemName) {
    super();
    componentId = ContainerizedAzure.getEventhubInstanceComponentId(liveSystemName);
  }
}
