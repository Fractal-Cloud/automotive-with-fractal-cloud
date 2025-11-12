package cloud.fractal.samples.automotive.environment;

import com.yanchware.fractal.sdk.domain.environment.CiCdProfile;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import cloud.fractal.samples.automotive.environment.configuration.EnvVarConfiguration;

import java.util.Collections;

public class App {
  public static void main(String[] args) throws InstantiatorException {
    var environmentsService = new Environments();
    var configuration = new EnvVarConfiguration();

    var defaultCiCdProfile = new CiCdProfile(
      "default",
      "Default",
      configuration.getPrivateSshKeySecretValue(),
      configuration.getPrivateSshPassphraseSecretValue());

    var environments = switch (configuration.getEnvironment()) {
      case PRODUCTION -> new AutomotiveEnvironment[] {
        environmentsService.productionAudi(defaultCiCdProfile, Collections.emptyList()),
        environmentsService.productionToyota(defaultCiCdProfile, Collections.emptyList())
      };
      default -> new AutomotiveEnvironment[] {
        environmentsService.stagingAudi(defaultCiCdProfile, Collections.emptyList()),
        environmentsService.stagingToyota(defaultCiCdProfile, Collections.emptyList())
      };
    };

    for(var environment:  environments) {
      environment.instantiate();
    }
  }
}