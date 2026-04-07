#!/usr/bin/env bash
BUILD_LOG="$1"
echo "[MobileCodeStudio-AI-HOOK] Build failed → Running Doctor..."
bash "$HOME/.mcs/tools/mcs-ai/bin/mcs-ai" --log "$BUILD_LOG"
bash "$HOME/.mcs/tools/mcs-ai/doctor/sdk-suggest.sh"
bash "$HOME/.mcs/tools/mcs-ai/doctor/dependency-fix.sh"
bash "$HOME/.mcs/tools/mcs-ai/doctor/manifest-auto-gen.sh"
bash "$HOME/.mcs/tools/mcs-ai/doctor/agp-aligner.sh"
