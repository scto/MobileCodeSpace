#!/usr/bin/env bash

LOG="$1"

grep -i "Unresolved reference" "$LOG" | while read -r line; do
  FILE=$(echo "$line" | awk -F: '{print $1}')
  SYMBOL=$(echo "$line" | sed -E 's/.*Unresolved reference:[[:space:]]*//g' | tr -d '\r')

  if [[ -n "$FILE" && -n "$SYMBOL" ]]; then
    echo "$FILE|$SYMBOL"
  fi
done
