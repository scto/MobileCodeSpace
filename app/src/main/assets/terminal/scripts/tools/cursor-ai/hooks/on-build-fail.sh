#!/usr/bin/env bash
LOG="$1"
echo "[Cursor-AI Hook] Build failed log: $LOG"
"$HOME/.mcs/tools/cursor-ai/bin/cursor-ai.sh"
