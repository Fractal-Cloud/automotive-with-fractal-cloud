#!/usr/bin/env bash

currentPath=$(pwd)
cd application/infrastructure/ || exit
gradle clean test distTar
cd build/distributions/ || exit
tar -xvzf app-infrastructure.tar
ENVIRONMENT="$1" \
  CI_CD_SERVICE_ACCOUNT_NAME="$(cat $currentPath/secrets/cicdServiceAccountName.key)" \
  CI_CD_SERVICE_ACCOUNT_SECRET="$(cat $currentPath/secrets/cicdServiceAccountSecret.key)" \
  ./app-infrastructure/bin/app-infrastructure
cd $currentPath || exit