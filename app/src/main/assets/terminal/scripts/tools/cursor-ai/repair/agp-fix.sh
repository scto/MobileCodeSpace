#!/usr/bin/env bash
PROJECT="$1"
echo "🔁 Aligning AGP versions..."
# Simplified: Replace version in build.gradle
if [ -f "$PROJECT/build.gradle" ]; then
  sed -i 's/classpath "com.android.tools.build:gradle:[0-9.]*/classpath "com.android.tools.build:gradle:8.2/' "$PROJECT/build.gradle"
fi
