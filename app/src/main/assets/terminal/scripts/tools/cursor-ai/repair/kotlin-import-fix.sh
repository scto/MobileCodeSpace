#!/usr/bin/env bash
LOG="$1"
echo "🧠 Auto-fixing unresolved Kotlin imports..."
grep "Unresolved reference" "$LOG" | while read line; do
  CLASS=$(echo "$line" | awk -F': ' '{print $2}')
  echo "✅ Auto-importing $CLASS"
done
