#!/usr/bin/env bash

currentPath=$(pwd)
cd environment/app/ || exit
gradle clean test distTar
cd build/distributions/ || exit
tar -xvzf environment-app.tar
ENVIRONMENT="$1" \
  PRIVATE_SSH_KEY_SECRET_VALUE="$(cat $currentPath/secrets/githubSshKey.key)" \
  PRIVATE_SSH_PASSPHRASE_SECRET_VALUE="$(cat $currentPath/secrets/githubSshKeyPassphrase.key)" \
  CI_CD_SERVICE_ACCOUNT_NAME="$(cat $currentPath/secrets/cicdServiceAccountName.key)" \
  CI_CD_SERVICE_ACCOUNT_SECRET="$(cat $currentPath/secrets/cicdServiceAccountSecret.key)" \
  GCP_SERVICE_ACCOUNT_EMAIL="$(cat $currentPath/secrets/gcpServiceAccountEmail.key)" \
  GCP_SERVICE_ACCOUNT_CREDENTIALS="$(cat $currentPath/secrets/gcpServiceAccountCredentials.key | base64)" \
  AZURE_SP_CLIENT_ID="$(cat $currentPath/secrets/azureServicePrincipalClientId.key)" \
  AZURE_SP_CLIENT_SECRET="$(cat $currentPath/secrets/azureServicePrincipalClientSecret.key)" \
  ./environment-app/bin/environment-app
cd $currentPath || exit