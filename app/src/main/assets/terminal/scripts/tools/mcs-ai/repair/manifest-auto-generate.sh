#!/usr/bin/env bash

MANIFEST="app/src/main/AndroidManifest.xml"

[ -f "$MANIFEST" ] && exit 0

PKG=$(grep applicationId app/build.gradle | awk -F\" '{print $2}')

echo "🧩 Generating AndroidManifest.xml"

mkdir -p app/src/main

cat << EOM > "$MANIFEST"
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="$PKG">

    <application
        android:allowBackup="true"
        android:label="App"
        android:theme="@style/Theme.AppCompat.Light">

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

    </application>
</manifest>
EOM
