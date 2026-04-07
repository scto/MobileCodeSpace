#!/usr/bin/env bash
echo "[REPAIR] AGP fix"

SETTINGS=$(find . -name settings.gradle | head -n1)
[ -z "$SETTINGS" ] && exit 0

sed -i 's/com.android.tools.build:gradle:.*/com.android.tools.build:gradle:8.2.2/' "$SETTINGS"
