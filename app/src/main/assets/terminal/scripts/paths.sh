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

# distro
export DISTRO_DIR="${DISTROS}/rootfs"

# base
export PATH="/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin"
export PATH="$PATH:/system/bin:/system/xbin:/usr/games:/usr/local/games:/snap/bin"
export PATH="$PATH:$DISTROS/_rkb"

# cmdline tools
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin

# jdk
mkdir -p "$PRE_JAVA_HOME"
if [ -f "$PRE_JAVA_HOME/version.txt" ]; then
  path2jdk="$(< "$PRE_JAVA_HOME/version.txt")"
  
  if [ -f "${path2jdk}/bin/java" ] || [ -f "${path2jdk}/scto/sh/java" ]; then
    export JAVA_HOME="${path2jdk}"
    export PATH="$JAVA_HOME/bin:$JAVA_HOME/scto/sh:$PATH"
  fi
else
  mcs switchjdk 17
fi

# other
export DEBIAN_FRONTEND=noninteractive
export APT_PAGER=cat
export XPWD=$PWD
export PIP_BREAK_SYSTEM_PACKAGES=1
export ANDROID_PRINTF_LOG=all
export LD_WARN=0
export BASHRC=${HOME}/.bashrc
