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

clear
echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" 2>&1 | tee "$TMP_TASKS_FILE"
echo "| • PROJECT CONFIGURATION • |" 2>&1 | tee -a "$TMP_TASKS_FILE"
echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" 2>&1 | tee -a "$TMP_TASKS_FILE"
echo "" 2>&1 | tee -a "$TMP_TASKS_FILE"
bash gradlew -I "$SCRIPTS/gradle/mcs-init.gradle" --console=plain 2>&1 | tee -a "$TMP_TASKS_FILE"
echo "... task init finished ..." 2>&1 | tee -a "$TMP_TASKS_FILE"
