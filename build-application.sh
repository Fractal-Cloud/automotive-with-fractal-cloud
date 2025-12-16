#!/usr/bin/env bash

currentPath=$(pwd)
cd application/reader/app || exit
gradle clean distTar
docker buildx build -t crautomotivestaging.azurecr.io/reader-app:latest --platform linux/amd64,linux/arm64 . --push

cd $currentPath || exit

cd application/writer/app || exit
gradle clean distTar
docker buildx build -t crautomotivestaging.azurecr.io/writer-app:latest --platform linux/amd64,linux/arm64 . --push

cd $currentPath || exit