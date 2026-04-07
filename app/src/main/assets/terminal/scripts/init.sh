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

update-alternatives --remove-all java 2>/dev/null
update-alternatives --remove-all javac 2>/dev/null

. $SCRIPTS/clrs.sh
. $SCRIPTS/links.sh
. $SCRIPTS/paths.sh

alias ls='ls --color=auto'
alias ll='ls -alF'
alias la='ls -A'
alias l='ls -CF'

if [[ "${pjct}" == /storage/emulated/0* ]]; then
    export pjct="/sdcard${pjct:19}"
fi

cd "${pjct}"

export PS1="${NC}"
if [ "${cpl}" = "false" ]; then
    if [ "${CONSOLE_ENV}" = "true" ]; then
        . $MCS_HOME/caches/currentConsole.sh
    else
        . $SCRIPTS/msgw.sh
    fi
else
    export HISTFILE="$TMP_DIR/bash/mcs"
    export HISTSIZE=5000
    export HISTFILESIZE=5000
    set -o history
    trap 'rm -f "$HISTFILE"' EXIT
    
    stty -echo
fi

if [ -n "$NEW_JAVA_INIT" ]; then
    case "$NEW_JAVA_INIT" in
        switchjdk)
            mcs switchjdk "$MCS_VALUE"
            ;;
    esac
    unset NEW_JAVA_INIT
fi

if [ "$NEW_JAVA_VERSION_SET" = "true" ]; then
    cd ${TMP_CURRENT_DIR}
    
    unset TMP_CURRENT_DIR
    unset NEW_JAVA_VERSION_SET
    
    log "JAVA_HOME set to ${INFO}$JAVA_HOME"
    echo ""
    java --version
    
    echo ""
    
    log "${WARNING}Terminal environment reloaded"
fi
