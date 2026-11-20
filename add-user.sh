#!/usr/bin/env bash
# Adds an application user with role "user" to the local WildFly installation (ApplicationRealm).
# Usage: ./add-user.sh <user> <password>
set -euo pipefail

if [ $# -ne 2 ]; then
    echo "Usage: $0 <user> <password>" >&2
    exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Locate the WildFly installation unpacked by the Maven build
JBOSS_HOME="$(ls -d "$SCRIPT_DIR"/server/wildfly-* 2>/dev/null | sort -V | tail -n 1 || true)"
if [ -z "$JBOSS_HOME" ] || [ ! -x "$JBOSS_HOME/bin/add-user.sh" ]; then
    echo "No WildFly installation found in $SCRIPT_DIR/server, run the Maven build first." >&2
    exit 1
fi

"$JBOSS_HOME/bin/add-user.sh" -a -u "$1" -p "$2" -g user
