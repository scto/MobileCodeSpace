#!/usr/bin/env bash
PROJECT="$1"
echo "🔍 Scanning Gradle files in $PROJECT"
if [ ! -f "$PROJECT/build.gradle" ] && [ ! -f "$PROJECT/build.gradle.kts" ]; then
  echo "⚠️ build.gradle missing"
fi
