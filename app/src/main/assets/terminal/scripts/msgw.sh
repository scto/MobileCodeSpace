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
. $SCRIPTS/links.sh
. $SCRIPTS/paths.sh

source .bashrc 2>/dev/null
export PS1="\[\033[38;2;3;96;87m\]\342\224\214\342\224\200\$([[ \$? != 0 ]] && log \"(\[\033[0;31m\]✗\[\033[38;2;3;96;87m\])\342\224\200\")(\[\033[38;2;31;163;151m\]\[\033[1m\]\u\[\033[22m\]\[\033[38;2;3;96;87m\]@\[\033[38;2;31;163;151m\]\[\033[1m\]rkb\[\033[22m\]\[\033[38;2;3;96;87m\])\342\224\200[ \[\033[38;2;0;180;0m\]\[\033[1m\]\w\[\033[22m\]\[\033[38;2;3;96;87m\] ]\n\[\033[38;2;3;96;87m\]\342\224\224\342\224\200\342\224\200\342\225\274 \[\033[0m\]\$ "
clear

if [ ! "$NEW_JAVA_VERSION_SET" = "true" ]; then
    log " • Welcome to MobileCodeStudio Terminal • "
    log ""
    
    if [ ! -f "$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager"  ]; then
        _warning "'cmdline-tools' ${NC}not Installed yet"
    fi

    if [ ! -f "$JAVA_HOME/bin/java" ]; then
        _warning "'JDK' ${NC}not Installed yet use : "
        _info2 "rkb setup ide"
        log ""
    fi
fi