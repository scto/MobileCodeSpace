#!/usr/bin/env bash
LOG="$1"

grep -q "Namespace not specified" "$LOG" && echo "FIX:NAMESPACE"
grep -q "compileSdk" "$LOG" && echo "FIX:COMPILE_SDK"
grep -q "com.android.tools.build:gradle" "$LOG" && echo "FIX:AGP"
