#!/usr/bin/env bash

MANIFEST=$(find . -name AndroidManifest.xml | head -n1)

if [ -z "$MANIFEST" ]; then
  echo "❌ AndroidManifest.xml missing"
else
  echo "✓ AndroidManifest found: $MANIFEST"
fi
