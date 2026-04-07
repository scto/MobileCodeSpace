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



FORCE_ENABLED=false
ORACLE_ENABLED=false
cmd=""
data=""





_help(){
  log "${BOLD}MobileCodeStudio : MCS Script${NC}"
  log "This script is designed to install and configure essential tools for Android development."
  echo ""
  log "${BOLD}Usage:${NC} mcs <command> [options] <data>"
  echo ""
  log "${BOLD}Commands:"
  log "  install     : Install tools such as NDK, JDK (17, 21, 22, 23, 24, 25)"
  log "  setup       : Set up the MobileCodeStudio tools, including IDE and environment"
  log "  switchjdk   : Switch between different versions of JDK"
  log ""
  log "${BOLD}Options:"
  log "  -f|--force  : Force installation even if the version is already installed"
  log "  -o|--oracle : To install the JDK (-o to download the Oracle version)."
  log "  -h|--help   : Display this help message"
  echo ""
  log "${BOLD}Example usage:"
  log "  ${CYAN}mcs install jdk17${NC}          # Install JDK 17 from apt"
  log "  ${CYAN}mcs -o install jdk17${NC}       # Install JDK 17 from oracle"
  log "  ${CYAN}apt install openjdk-17-jdk${NC} # Install JDK 17 from apt"
  log "  ${CYAN}mcs install cmdline${NC}        # Install Cmdline"
  log "  ${CYAN}mcs install ndk${NC}            # Install Ndk"
  log "  ${CYAN}mcs setup ide${NC}              # Set up the IDE environment"
  log "  ${CYAN}mcs switchjdk 21${NC}           # Switch to JDK 21"
  echo ""
  log "${BOLD}Available"
  log "${WARNING}From orable : ${NC}17, 21, 22, 23, 24(64-bits only)"
  log "${WARNING}From apt    : ${NC}8, 17, 21, 25 "
  log "(${GREEN}'${BOLD}apt search jdk'${NC} for more details)."
}



OPTS=$(getopt -o foh -l force,oracle,help -- "$@") || exit 1
eval set -- "$OPTS"

while true; do
  case "$1" in
    -f|--force) FORCE_ENABLED=true; shift ;;
    -o|--oracle) ORACLE_ENABLED=true; shift ;;
    -h|--help) _help; exit 0 ;;
    --) shift; break ;;
  esac
done



cmd="$1"
data="$2"



install_openjdk() {
  local TCP="$PWD"
  URL="$1"
  ARCHIVE_NAME="$(basename $URL)"
  
  if [ "$FORCE_ENABLED" = "false" ]; then
    version=$(get_jdk_version "$URL")
    if [ -f "$PRE_JAVA_HOME/$version/bin/java" ] || [ -f "$PRE_JAVA_HOME/$version/jre/sh/java" ]; then
      _warning "This version ${NC}($version) ${WARNING}is already installed"
      log "Add ${Warning}-f${NC} after ${Warning}mcs${NC} to force the installation"
      return 1
    fi
  fi
    
  mkdir -p "$PRE_JAVA_HOME"
  cd "$PRE_JAVA_HOME" || return 1

  log "Cleaning previous JDK installation..."
  rm -rf "$ARCHIVE_NAME"

  log "Downloading JDK from ${INFO}$URL..."
  
  wget -O "$ARCHIVE_NAME" "$URL" || {
    _warning "Download failed !"
    return 1
  }

  log "Extracting the archive..."
  if tar -xf "$ARCHIVE_NAME"; then
    _success "JDK installed"
    _switchjdk 17
  else
    _error "Extraction failed !"
    return 1
  fi

  if [ ! -f "$JAVA_HOME/bin/java" ] && [ ! -f "$JAVA_HOME/jre/sh/java" ]; then
    _error "[!!] The JDK not properly installed and configured."
    info "i" "Try again to reinstall (mcs install jdk17)."
  fi
    
  update-alternatives --remove-all java 2>/null
  update-alternatives --remove-all javac 2>/null
  
  cd "$TCP"
}



_setupEnv(){
  . $SCRIPTS/init-distro.sh
}



_setupIde(){
  _setupEnv
  
  if [ "$ORACLE_ENABLED" = "false" ]; then
    yes | apt remove openjdk-17-jdk
    yes | apt install openjdk-17-jdk
    _switchjdk 17
  else
    install_openjdk "$jdk17"
  fi
  
  . $SCRIPTS/cmdline-tools.sh
  . $SCRIPTS/paths.sh
  
  if [ -f "$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager" ] && [ -f "$PRE_JAVA_HOME/17.0.12/bin/java" ]; then
  . $SCRIPTS/paths.sh
    echo ""
    _success "[✓] ide setup finished"
    echo ""
  else
    if [ ! -f "$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager" ]; then
      _error "[!!] The cmdline not properly installed and configured."
      info "i" "Try again to reinstall (mcs install cmdline)."
    fi
  fi
  
  update-alternatives --remove-all java 2>/null
  update-alternatives --remove-all javac 2>/null
  
  _info "A small upgrade and everything will be ready."
  yes | apt upgrade
  _info "Configuration complete !"
  log "*" "If a particular tool has not been installed correctly,"
  echo "  -  Rerun the order or install it individually."
}



_switchjdk() {
    if [ -z "$1" ]; then
        log "Usage: switchjdk <version>"
        log "Example: switchjdk 17 # 21, 22, 23, ..."
        return 1
    fi
    
    update-alternatives --remove-all java 2>/null
    update-alternatives --remove-all javac 2>/null

    VERSION=$1
    CURRENT_FILE="$PRE_JAVA_HOME/version.txt"
    JVM_PATH="${DISTRO_DIR}/usr/lib/jvm"
    
    if [ "$ARCH_APP" = "32" ]; then
        if [ -f "${JVM_PATH}/java-${VERSION}-openjdk-armhf/bin/java" ]; then
          JVM_PATH="${JVM_PATH}/java-${VERSION}-openjdk-armhf"
        else
          JVM_PATH=""
        fi
    else
        if [ -f "${JVM_PATH}/java-${VERSION}-openjdk-arm64/bin/java" ]; then
          JVM_PATH="${JVM_PATH}/java-${VERSION}-openjdk-arm64"
        else
          JVM_PATH=""
        fi
    fi
    
    if [ -z "$JVM_PATH" ]; then
        _warning "[x] No JDK matching '$VERSION' found."
        log "install a new jdk with this command"
        log "'mcs install jdk17' or 'apt install openjdk-17-jdk'"
        return 1
    fi

    mkdir -p "$PRE_JAVA_HOME"
    export JAVA_HOME="${JVM_PATH}"
    echo "$JVM_PATH" > "$CURRENT_FILE"
    if [ "$2" = "true" ]; then
        echo "This ${BOLD}task${NC} will ${BOLD}reload${NC} the environment,"
        read -p "Do you want to continue ? (y/n) : " yn
        case $yn in
            [Yy]*) echo "in process" ;;
                *) _error "task cancelled"; return 1 ;;
        esac 

        export NEW_JAVA_VERSION_SET="true"
        export TMP_CURRENT_DIR="$PWD"
        exec bash --rcfile "$SCRIPTS/init.sh"
    fi
}



case "$cmd" in
  "init")
    . $SCRIPTS/init.sh
  ;;
  "switchjdk")
    _switchjdk $2 true
  ;;
  install)
    if [ "$data" = "ndk" ]; then
      if [ -f "$HOME/ndk-installer.sh" ]; then rm $HOME/ndk-installer.sh; fi
      wget https://github.com/scto/MobileCodeStudio-NDK/raw/main/ndk-install.sh --no-verbose --show-progress -N
      chmod +x $HOME/ndk-install.sh
      bash $HOME/ndk-install.sh
    elif [ "$data" = "cmdline" ]; then
      . $SCRIPTS/cmdline-tools.sh "$FORCE_ENABLED"
    elif [ "$data" = "jdk25" ]; then
        if [ "$ORACLE_ENABLED" != "true" ]; then
          yes | apt install openjdk-25-jdk
          _switchjdk 25 true
        else
          _warning "not support install from ${ERROR}apt${WARNING} for this version"
        fi
    elif [ "$data" = "jdk24" ]; then
        if [ "$ORACLE_ENABLED" != "true" ]; then
          _warning "not support install from ${ERROR}mcs -o install ...${WARNING} for this version"
        else
          install_openjdk "$jdk24"
          _switchjdk 24 true
        fi
    elif [ "$data" = "jdk23" ]; then
        if [ "$ORACLE_ENABLED" != "true" ]; then
          _warning "not support install from ${ERROR}mcs -o install ...${WARNING} for this version"
        else
          install_openjdk "$jdk23"
          _switchjdk 23 true
        fi
    elif [ "$data" = "jdk22" ]; then
        if [ "$ORACLE_ENABLED" != "true" ]; then
          _warning "Not support install from ${ERROR}mcs -o install ...${WARNING} for this version"
        else
          install_openjdk "$jdk22"
          _switchjdk 22 true
        fi
    elif [ "$data" = "jdk21" ]; then
        if [ "$ORACLE_ENABLED" != "true" ]; then
          yes | apt install openjdk-21-jdk
        else
          install_openjdk "$jdk21"
        fi
        _switchjdk 21 true
    elif [ "$data" = "jdk17" ]; then
        if [ "$ORACLE_ENABLED" != "true" ]; then
          yes | apt install openjdk-17-jdk
        else
          install_openjdk "$jdk17"
        fi
        _switchjdk 17 true
    else
        info "*" "Use '--help for more details"
    fi
  ;;
  setup)
    if [ "$data" = "ide" ]; then
      _setupIde
    elif [ "$data" = "env" ]; then
      _setupEnv
    elif [ "$data" = "jvm" ]; then
      yes | apt install nodejs npm python3 openjdk-17-jdk kotlin gcc g++ groovy
    elif [ "$data" = "lsp" ]; then
      tmp_var="$PWD"
      cd ${HOME}
      yes | apt install wget
      wget ${lspLibs}
      echo "File extraction in progress, please wait."
      yes | unzip -o "libs.zip" -d "${MobileCodeStudio_HOME}/caches"
      echo ""
      _info2 "Everything has been downloaded and configured"
      cd ${tmp_var}
    else
      info "*" "Use 'mcs setup ide' or --help"
    fi
  ;;
  --help|-h)
  _help
  ;;
  *)
    echo "use 'mcs -h or --help' for more details"
  ;;
esac
