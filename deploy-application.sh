#!/usr/bin/env bash

currentPath=$(pwd)
cd architecture/app/ || exit
gradle clean test distTar
cd build/distributions/ || exit
tar -xvzf architecture.tar
ENVIRONMENT="$1" \
  CI_CD_SERVICE_ACCOUNT_NAME="$(cat $currentPath/secrets/cicdServiceAccountName.key)" \
  CI_CD_SERVICE_ACCOUNT_SECRET="$(cat $currentPath/secrets/cicdServiceAccountSecret.key)" \
  ./architecture/bin/architecture
cd $currentPath || exit