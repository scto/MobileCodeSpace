#!/usr/bin/env bash
echo "[REPAIR] Namespace fix"

APP=$(find . -name build.gradle | head -n1)
[ -z "$APP" ] && exit 0

grep -q "namespace" "$APP" || \
sed -i '/android {/a\    namespace "com.androidpe.fixed"' "$APP"
