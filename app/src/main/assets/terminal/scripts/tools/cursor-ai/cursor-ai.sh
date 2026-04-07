#!/usr/bin/env bash

PROJECT="$PWD"
BASE="$HOME/.mcs/tools/cursor-ai"
LOG_DIR="$BASE/logs"
mkdir -p "$LOG_DIR"

LOG_FILE="$LOG_DIR/run-$(date +%F-%H%M%S).log"

echo "[Cursor-AI] Project: $PROJECT" | tee -a "$LOG_FILE"

run() {
  bash "$BASE/$1" "$PROJECT" 2>&1 | tee -a "$LOG_FILE"
}

run scanner/gradle-scan.sh
run scanner/manifest-scan.sh
run scanner/sdk-scan.sh
run scanner/deps-scan.sh

run repair/gradle-repair.sh
run repair/manifest-repair.sh
run repair/namespace-repair.sh
run repair/sdk-repair.sh

bash "$BASE/brain/explain.sh" "$LOG_FILE"

echo "[Cursor-AI] Done" | tee -a "$LOG_FILE"
