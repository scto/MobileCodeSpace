#!/usr/bin/env bash
PROJECT="$PWD"

echo "🔧 Checking dependency conflicts..."

LIBS=$(grep -R "implementation .*:" app/build.gradle | awk -F"'" '{print $2}' | sort)

echo "$LIBS" | awk -F: '{print $1":"$2}' | uniq -d | while read lib; do
  BEST=$(echo "$LIBS" | grep "$lib" | sort -V | tail -n1)
  echo "✅ Resolving $lib → $BEST"
  sed -i "/$lib:/d" app/build.gradle
  sed -i "/dependencies {/a\    implementation '$BEST'" app/build.gradle
done
