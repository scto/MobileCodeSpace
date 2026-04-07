#!/usr/bin/env bash
PROJECT="$1"
echo "🔧 Checking dependency versions..."
grep "implementation" "$PROJECT/app/build.gradle" 2>/dev/null | sort | uniq -c
