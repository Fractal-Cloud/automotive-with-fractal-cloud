package cloud.fractal.samples.automotive.environment;

import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;

import java.util.Collection;

public interface AutomotiveSystem {
  LiveSystemIdValue liveSystemId();
  FractalIdValue fractalId();
  String description();
  Collection<? extends LiveSystemComponent> components();
}
