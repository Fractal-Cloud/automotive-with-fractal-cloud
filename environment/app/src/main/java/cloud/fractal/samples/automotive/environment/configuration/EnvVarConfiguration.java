package cloud.fractal.samples.automotive.environment.configuration;

public class EnvVarConfiguration implements Configuration {
  @Override
  public Environment getEnvironment() {
    var environmentStr = getVariableValue("ENVIRONMENT", true);
    return Environment.valueOf(environmentStr);
  }

  @Override
  public String getPrivateSshKeySecretValue() {
    return getVariableValue("PRIVATE_SSH_KEY_SECRET_VALUE", true);
  }

  @Override
  public String getPrivateSshPassphraseSecretValue() {
    return getVariableValue("PRIVATE_SSH_PASSPHRASE_SECRET_VALUE", true);
  }

  public String getVariableValue(String key, boolean throwOnMissing) {
    var value = System.getenv(key);

    if ((value == null || value.isBlank()) && throwOnMissing) {
      throw new IllegalArgumentException(
        String.format("The environment variable or system property ['%s'] is required but it has not been defined",
          key));
    }

    return value;
  }
}
