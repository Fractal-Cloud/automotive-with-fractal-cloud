package cloud.fractal.samples.automotive.architecture.livesystems;

import com.yanchware.fractal.sdk.domain.ComponentLink;

public class ReceiverLink extends ComponentLink {
  public ReceiverLink(String liveSystemName) {
    super();
    componentId = ContainerizedAzure.getEventhubInstanceComponentId(liveSystemName);
    settings.put("roleName", "Azure Event Hubs Data Receiver");
  }
}
