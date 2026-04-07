#!/usr/bin/env bash

LOG="$1"

echo ""
echo "🧠 AI EXPLANATION (Human Friendly)"
echo "--------------------------------"

if grep -qi "compileSdk" "$LOG"; then
  echo "❌ Problem: compileSdk missing"
  echo "✅ Fix:"
  echo "android { compileSdk = 34 }"
fi

if grep -qi "Unresolved reference" "$LOG"; then
  echo "❌ Kotlin error: Unresolved reference"
  echo "✅ Fix:"
  echo "• Missing import OR dependency"
  echo "• Check build.gradle dependencies"
fi

if grep -qi "AndroidManifest.xml" "$LOG"; then
  echo "❌ Manifest issue detected"
  echo "✅ Fix:"
  echo "• package / namespace mismatch"
fi
