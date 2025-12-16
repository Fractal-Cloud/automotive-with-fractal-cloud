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

branchName="env/audi-staging"
branchNameExists=$(git ls-remote origin $branchName)

if [ -z "$branchNameExists" ]; then
  echo "Git branch '$branchName' does not exist in the remote repository"
else
  echo "Git branch '$branchName' exists in the remote repository, deleting"
  git branch -d $branchName
  git push origin --delete $branchName
fi

git checkout -b $branchName

git add .
git push --set-upstream origin $branchName
git checkout main