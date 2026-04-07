#!/usr/bin/env bash
PROJECT="$1"
if [ ! -f "$PROJECT/app/src/main/AndroidManifest.xml" ]; then
  echo "⚠️ AndroidManifest missing"
fi
