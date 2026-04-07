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

# === COLORS ===
ESC="$(printf '\033')"

# standard colors
RED="${ESC}[0;31m"
GREEN="${ESC}[0;32m"
YELLOW="${ESC}[0;33m"
BLUE="${ESC}[0;34m"
MAGENTA="${ESC}[0;35m"
CYAN="${ESC}[0;36m"
BLACK="${ESC}[0;30m"

# light colors
LIGHT_RED="${ESC}[1;31m"
LIGHT_GREEN="${ESC}[1;32m"
LIGHT_YELLOW="${ESC}[1;33m"
LIGHT_BLUE="${ESC}[1;34m"
LIGHT_MAGENTA="${ESC}[1;35m"
LIGHT_CYAN="${ESC}[1;36m"

# bold colors (same as light in ANSI, but naming kept for clarity)
BRED="${ESC}[1;31m"
BGREEN="${ESC}[1;32m"
BYELLOW="${ESC}[1;33m"
BBLUE="${ESC}[1;34m"
BMAGENTA="${ESC}[1;35m"
BCYAN="${ESC}[1;36m"

# log aliases
ERROR="$LIGHT_RED"
WARNING="$LIGHT_YELLOW"
SUCCESS="$LIGHT_GREEN"
INFO="$LIGHT_BLUE"

# reset
NC="${ESC}[0m"

# default bold
BOLD="${ESC}[1m"

# === METHODS ===
# default log
log(){
  echo -e "$@ ${NC}"
}

# info
info(){
  log "${INFO}[$1] ${NC}$2"
}

# info - full
_info(){
  log "${INFO}$@"
}

# info
info2(){
  log "${BCYAN}[$1] ${NC}$2"
}

# info - full
_info2(){
  log "${BCYAN}$@"
}

# warning
warning(){
  log "${WARNING}[$1] ${NC}$2"
}

# warning - full
_warning(){
  log "${WARNING}$@"
}

# error
error(){
  log "${ERROR}[$1] ${NC}$2"
}

# error - full
_error(){
  log "${ERROR}$@"
}
# success
success(){
  log "${SUCCESS}[$1] ${NC}$2"
}

# success - full
_success(){
  log "${SUCCESS}$@"
}