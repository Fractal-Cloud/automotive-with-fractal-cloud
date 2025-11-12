package cloud.fractal.samples.automotive.environment.configuration;

public interface Configuration {
  Environment getEnvironment();

  String getPrivateSshKeySecretValue();

  String getPrivateSshPassphraseSecretValue();
}
