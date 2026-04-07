#!/usr/bin/env bash

if [ ! -f settings.gradle ] && [ ! -f settings.gradle.kts ]; then
  echo "⚠️ Creating minimal settings.gradle"
  echo 'rootProject.name="MCSProject"' > settings.gradle
fi
