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

ROOTFS="$DISTROS/rootfs"

REQUIRED_PKGS="
locales
apt-utils
ca-certificates
xz-utils
tar
zip
bash
unzip
adb
pv
sudo
curl
wget
gpg
"

UBUNTU_KEY_ID="3B4FE6ACC0B21F32"
UBUNTU_KEYRING="$ROOTFS/etc/apt/trusted.gpg.d/ubuntu.gpg"

[ -d "$ROOTFS" ] || exit 1

mkdir -p "$ROOTFS/tmp"
chmod 1777 "$ROOTFS/tmp"

touch "$ROOTFS/etc/gshadow"
chmod 640 "$ROOTFS/etc/gshadow" || true
chown root:shadow "$ROOTFS/etc/gshadow" 2>/dev/null || true

echo "in process wait ..."

apt purge systemd* -y 2>/dev/null
apt-mark hold systemd systemd-sysv systemd-timesyncd systemd-resolved 2>/dev/null
apt-mark hold systemd systemd-sysv 2>/dev/null
dpkg --configure -a 2>/dev/null
apt -f install

dpkg --configure -a || true

yes | apt update || true
yes | apt install -y ca-certificates
update-ca-certificates
yes | apt install gpg

if [ ! -s ${ROOTFS}/etc/ssl/certs/ca-certificates.crt ]; then
  echo "SSL not ready"
  exit 1
fi

import_gpg_key() {
  if [ -s "$UBUNTU_KEYRING" ]; then
    return
  fi
 
  echo ""
  _warning "Please wait !"
  log "${BOLD}If a GPG key is missing, it will be automatically installed"
  echo ""
  
  rm -f "$UBUNTU_KEYRING"
  mkdir -p "$(dirname "$UBUNTU_KEYRING")"

  curl -fsSL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x$UBUNTU_KEY_ID" \
    | gpg --dearmor -o "$UBUNTU_KEYRING"
}

install_base_libs() {
  local missing=""
  for pkg in $REQUIRED_PKGS; do
    dpkg -s "$pkg" >/dev/null 2>&1 || missing="$missing $pkg"
  done
  [ -n "$missing" ] && apt install -y $missing
}

import_gpg_key
install_base_libs
