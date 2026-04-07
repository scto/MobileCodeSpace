#!/usr/bin/env bash

WRAPPER="gradle/wrapper/gradle-wrapper.properties"
BUILD="build.gradle"

AGP=$(grep "com.android.tools.build:gradle" build.gradle | awk -F: '{print $NF}' | tr -d "'")

case "$AGP" in
  8.*) GRADLE="8.2" ;;
  7.*) GRADLE="7.6" ;;
  *) GRADLE="8.2" ;;
esac

echo "🔁 Aligning Gradle for AGP $AGP → $GRADLE"

sed -i "s|distributionUrl=.*|distributionUrl=https\\://services.gradle.org/distributions/gradle-$GRADLE-bin.zip|" "$WRAPPER"
