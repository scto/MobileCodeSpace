#!/usr/bin/env bash

MANIFEST=$(find . -name AndroidManifest.xml | head -n1)

if [ -z "$MANIFEST" ]; then
  echo "⚠️ Creating default AndroidManifest.xml"
  mkdir -p app/src/main
  cat <<MEOF > app/src/main/AndroidManifest.xml
<manifest package="com.androidpe.fixed">
    <application />
</manifest>
MEOF
fi
