#!/usr/bin/env bash

if [ -z "$ANDROID_SDK_ROOT" ]; then
  echo "❌ ANDROID_SDK_ROOT not set"
else
  echo "✓ ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT"
fi
