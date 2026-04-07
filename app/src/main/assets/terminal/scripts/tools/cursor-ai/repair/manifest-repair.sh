#!/usr/bin/env bash
PROJECT="$1"
if [ ! -f "$PROJECT/app/src/main/AndroidManifest.xml" ]; then
  mkdir -p "$PROJECT/app/src/main"
  echo "<manifest package='com.example.app' xmlns:android='http://schemas.android.com/apk/res/android'></manifest>" > "$PROJECT/app/src/main/AndroidManifest.xml"
  echo "⚡ Manifest auto-generated"
fi
