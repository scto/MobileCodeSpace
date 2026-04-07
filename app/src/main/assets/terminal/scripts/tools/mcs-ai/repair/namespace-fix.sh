#!/usr/bin/env bash
echo "Namespace repair (dry-run)..."

if grep -R "namespace" . >/dev/null 2>&1; then
  echo "Namespace already defined"
else
  echo "⚠️ Namespace missing (manual review needed)"
fi
