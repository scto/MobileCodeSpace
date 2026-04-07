#!/usr/bin/env bash
FILE=$(find . -name build.gradle | head -n 1)

if ! grep -q "compileSdk" "$FILE"; then
  sed -i '/android {/a\    compileSdk 34' "$FILE"
  echo "✔ compileSdk added (34)"
fi
