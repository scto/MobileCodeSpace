#!/usr/bin/env bash

LOG="$1"
MAP="$HOME/.mcs/tools/mcs-ai/brain/import-map.conf"

bash "$HOME/.mcs/tools/mcs-ai/scanner/kotlin-error-scan.sh" "$LOG" |
while IFS="|" read -r FILE SYMBOL; do
  IMPORT=$(grep "^$SYMBOL=" "$MAP" | cut -d= -f2)

  if [[ -z "$IMPORT" ]]; then
    echo "⚠️ No import rule for $SYMBOL"
    continue
  fi

  if [[ ! -f "$FILE" ]]; then
    echo "⚠️ Source file not found: $FILE (skip auto-patch)"
    continue
  fi

  if grep -q "$IMPORT" "$FILE"; then
    echo "ℹ️ Import already exists: $IMPORT"
  else
    echo "✅ Auto-importing $IMPORT into $FILE"
    sed -i "1i import $IMPORT" "$FILE"
  fi
done
