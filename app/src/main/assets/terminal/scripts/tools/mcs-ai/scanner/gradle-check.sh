#!/usr/bin/env bash

if ls build.gradle* settings.gradle* >/dev/null 2>&1; then
  echo "✓ Gradle files found"
else
  echo "⚠️ No root build.gradle / settings.gradle found"
fi
