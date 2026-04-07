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

chmod +x $SCRIPTS/gradle/mcs-init.gradle
chmod +x $SCRIPTS/_init-mcs-project.sh
chmod +x $SCRIPTS/args.sh
chmod +x $SCRIPTS/clrs.sh
chmod +x $SCRIPTS/cmdline-tools.sh
chmod +x $SCRIPTS/init-distro.sh
chmod +x $SCRIPTS/init-host.sh
chmod +x $SCRIPTS/init.sh
chmod +x $SCRIPTS/links.sh
chmod +x $SCRIPTS/msgw.sh
chmod +x $SCRIPTS/paths.sh
chmod +x $DISTROS/_mcs/mcs

. $SCRIPTS/args.sh
. $SCRIPTS/clrs.sh
. $SCRIPTS/paths.sh

if [ ! -f "$DISTROS/rootfs/bin/bash" ]; then
  _error "The rootfs was not installed correctly or is broken."
  _warning "Please reinstall it."
  log "Preferences ⟩ Terminal"
  log ""
exit 404; fi

exec $PROOT $ARGS /bin/bash --rcfile "$SCRIPTS/init.sh"
