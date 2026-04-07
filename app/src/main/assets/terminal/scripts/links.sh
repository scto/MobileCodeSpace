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

# === DISTRO ===
rootfs64="https://cdimage.ubuntu.com/ubuntu-base/releases/noble/release/ubuntu-base-24.04.3-base-arm64.tar.gz"
rootfs32="https://cdimage.ubuntu.com/ubuntu-base/releases/noble/release/ubuntu-base-24.04.3-base-armhf.tar.gz"


# === JDK ===
jdk17="https://download.oracle.com/java/17/archive/jdk-17.0.12_linux-aarch64_bin.tar.gz"
jdk21="https://download.oracle.com/java/21/archive/jdk-21.0.7_linux-aarch64_bin.tar.gz"
jdk22="https://download.oracle.com/java/22/archive/jdk-22.0.2_linux-aarch64_bin.tar.gz"
jdk23="https://download.oracle.com/java/23/archive/jdk-23.0.2_linux-aarch64_bin.tar.gz"
jdk24="https://download.oracle.com/java/24/latest/jdk-24_linux-aarch64_bin.tar.gz"


get_jdk_version() {
  url="$1"
  if [ "$url" = "$jdk17" ]; then echo "jdk-17.0.12"; fi
  if [ "$url" = "$jdk21" ]; then echo "jdk-21.0.7"; fi
  if [ "$url" = "$jdk22" ]; then echo "jdk-22.0.2"; fi
  if [ "$url" = "$jdk23" ]; then echo "jdk-23.0.2"; fi
  if [ "$url" = "$jdk24" ]; then echo "jdk-24"; fi
}


# === CMDLINE-TOOLS ===
cmdline="https://github.com/jkasdbt/AndroidPE-Tools/releases/download/cmdline/cmdline.zip"


# === LSP default data ===
lspLibs="https://github.com/jkasdbt/AndroidPE-Tools/raw/refs/heads/main/libs.zip"
