#!/usr/bin/env bash

ROOT="$HOME/.androidpe/tools/cursor-ai"
PROJECT="$PWD"
LOG="$ROOT/logs/run-$(date +%F-%H%M%S).log"
mkdir -p "$ROOT/logs"

echo "[Cursor-AI] Project: $PROJECT" | tee -a "$LOG"

run() {
  local f="$1"
  if [ -f "$f" ]; then
    echo "[Cursor-AI] Running: $(basename $f)" | tee -a "$LOG"
    bash "$f" "$PROJECT" 2>&1 | tee -a "$LOG"
  fi
}

# --- SCAN ---
run "$ROOT/scanner/gradle-scan.sh"
run "$ROOT/scanner/manifest-scan.sh"
run "$ROOT/scanner/sdk-scan.sh"
run "$ROOT/scanner/deps-scan.sh"

# --- REPAIR ---
run "$ROOT/repair/gradle-repair.sh"
run "$ROOT/repair/manifest-repair.sh"
run "$ROOT/repair/namespace-repair.sh"
run "$ROOT/repair/sdk-repair.sh"
run "$ROOT/repair/kotlin-import-fix.sh"
run "$ROOT/repair/deps-fix.sh"
run "$ROOT/repair/agp-fix.sh"

# --- DOCTOR / EXPLAIN ---
bash "$ROOT/brain/explain.sh" "$LOG"

echo "[Cursor-AI] Done" | tee -a "$LOG"
