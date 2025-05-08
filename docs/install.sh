#!/bin/bash

echo "📁 Currently your working dir is: $(pwd)"

dir="docs/docker"
files=("eventms-hub" "eventms-organizer")

for file in "${files[@]}"; do
  echo "🐳 Building docker image: $file"

  if [ ! -f "$dir/$file.Dockerfile" ]; then
    echo "❗ File $file.Dockerfile not found. Skipping."
    continue
  fi

  docker build -f "$dir/$file.Dockerfile" -t "$file:2.0-SNAPSHOT" .
done

echo "🚀 Running the docker compose"

if [ ! -f "$dir/docker-compose-env.yml" ]; then
  echo "❗ File docker-compose-env not found. Skipping."
  continue
fi

if [ ! -f "$dir/docker-compose-app.yml" ]; then
  echo "❗ File docker-compose-app not found. Skipping."
  continue
fi

docker compose -f "./$dir/docker-compose-env.yml" -f "./$dir/docker-compose-app.yml" up -d

echo "✅ Everything is done"
