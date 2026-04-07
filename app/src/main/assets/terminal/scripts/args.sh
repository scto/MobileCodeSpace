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

export DISTRO_NAME="rootfs"
export DISTRO_DIR="${DISTROS}/${DISTRO_NAME}"

ARGS="--kill-on-exit"
ARGS="$ARGS -w /"

for system_mnt in /apex /odm /product /system /system_ext /vendor \
 /linkerconfig/ld.config.txt \
 /linkerconfig/com.android.art/ld.config.txt \
 /plat_property_contexts /property_contexts; do

 if [ -e "$system_mnt" ]; then
  resolved=$(realpath "$system_mnt" 2>/dev/null)
  [ -n "$resolved" ] && ARGS="$ARGS -b ${resolved}"
 fi
done
unset system_mnt resolved

[ -d /storage/emulated/0 ] && ARGS="$ARGS -b /storage/emulated/0"
[ -d /sdcard ] && ARGS="$ARGS -b /sdcard"
[ -d /storage ] && ARGS="$ARGS -b /storage"
[ -d /dev ] && ARGS="$ARGS -b /dev"
[ -d /data ] && ARGS="$ARGS -b /data"
[ -e /dev/urandom ] && ARGS="$ARGS -b /dev/urandom:/dev/random"
[ -d /proc ] && ARGS="$ARGS -b /proc"
[ -e /proc/self/fd ] && ARGS="$ARGS -b /proc/self/fd:/dev/fd"
[ -e /proc/self/fd/0 ] && ARGS="$ARGS -b /proc/self/fd/0:/dev/stdin"
[ -e /proc/self/fd/1 ] && ARGS="$ARGS -b /proc/self/fd/1:/dev/stdout"
[ -e /proc/self/fd/2 ] && ARGS="$ARGS -b /proc/self/fd/2:/dev/stderr"
[ -d /sys ] && ARGS="$ARGS -b /sys"
[ -n "$HOME" ] && [ -e "$HOME" ] && ARGS="$ARGS -b $HOME"
[ -n "$PREFIX/usr" ] && [ -e "$PREFIX/usr" ] && ARGS="$ARGS -b $PREFIX/usr"
[ -n "$SCRIPTS" ] && [ -e "$SCRIPTS" ] && ARGS="$ARGS -b $SCRIPTS"
[ -e "$PREFIX/stat" ] && ARGS="$ARGS -b $PREFIX/stat:/proc/stat"
[ -e "$PREFIX/vmstat" ] && ARGS="$ARGS -b $PREFIX/vmstat:/proc/vmstat"

if [ -n "$TMP_DIR" ]; then
  if [ ! -d "$TMP_DIR" ]; then
    mkdir -p "$TMP_DIR"
    chmod 1777 "$TMP_DIR"
  fi
  [ -e "$TMP_DIR" ] && ARGS="$ARGS -b $TMP_DIR"
  [ -e "$TMP_DIR" ] && ARGS="$ARGS -b $TMP_DIR:/dev/shm"
fi

ARGS="$ARGS -0"
ARGS="$ARGS --link2symlink"
ARGS="$ARGS --sysvipc"
ARGS="$ARGS -L"
ARGS="$ARGS --rootfs=${DISTRO_DIR}"
