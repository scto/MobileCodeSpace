#!/usr/bin/env bash

if grep -R "com.android.tools.build:gradle" -n . >/dev/null 2>&1; then
  echo "✓ Android Gradle Plugin found"
else
  echo "⚠️ AGP not declared explicitly"
fi
