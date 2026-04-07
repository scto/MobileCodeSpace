#!/usr/bin/env bash

LOG="$1"

echo "🔍 Error scan started..."

grep -iE "compileSdk|namespace|Unresolved reference|Execution failed|Could not resolve" "$LOG" || echo "No known error patterns"
