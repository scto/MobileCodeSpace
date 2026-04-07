#!/usr/bin/env bash

if ! grep -R "compileSdk" -n . >/dev/null 2>&1; then
  echo "ℹ️ compileSdk missing (manual fix recommended)"
fi
