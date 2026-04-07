#!/usr/bin/env bash
LOG="$1"

echo ""
echo "[AI EXPLANATION]"
grep -E "❌|⚠️" "$LOG" || echo "✓ No critical issues detected"
