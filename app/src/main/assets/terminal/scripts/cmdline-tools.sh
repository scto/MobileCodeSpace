# MobileCodeStudio - Your IDE in your pocket !
# 
# MobileCodeStudio is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# MobileCodeStudio is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with MobileCodeStudio.  If not, see <https://www.gnu.org/licenses/>.

. $SCRIPTS/clrs.sh

force="$1"
URL="$cmdline"
DEST_DIR="$ANDROID_HOME/cmdline-tools/latest"
TMP_ZIP="$TMP_DIR/download/cmdline_tool.zip"
if [ "$force" = "true" ]; then rm -rf $DEST_DIR; fi
if [ ! -f "$DEST_DIR/bin/sdkmanager" ]; then
  if [ ! -d "$DEST_DIR" ]; then mkdir -p "$DEST_DIR"; fi
  info "*" "downloading cmdline-tools ..."
  yes | apt install wget
  if wget -q --no-verbose --show-progress -N -O "$TMP_ZIP" "$URL"; then
    info "*" "Extracting the archive ..."
      if unzip -q "$TMP_ZIP" -d "$DEST_DIR"; then
        mv "$ANDROID_HOME/cmdline-tools/latest/cmdline-tools/"* "$ANDROID_HOME/cmdline-tools/latest/"
        rm -r "$ANDROID_HOME/cmdline-tools/latest/cmdline-tools"
        info "*" "cleaning tmp archive..."
        rm "$TMP_ZIP"
        _success "cmdline-tools has been added"
        if [ -x "$DEST_DIR/bin/sdkmanager" ]; then
          log "[+] sdkmanager is ready to be used."
        else
          chmod +x "$DEST_DIR/bin/sdkmanager"
          if [ -x "$DEST_DIR/bin/sdkmanager" ]; then
            yes | sdkmanager --licenses
            log "[+] sdkmanager is now executable."
          else
            error "!" "Failed to make sdkmanager executable."
          fi    
        fi
      else
        _warning "[?] : can not extract $TMP_ZIP"
      fi
    else
      _error "cmdline-tools has not been downloaded correctly"
    fi
else
  _info "The cmdline is already installed !"
fi

if command -v sdkmanager >/dev/null 2>&1; then
  (yes | sdkmanager --licenses >/dev/null 2>&1) & disown
fi
