#!/usr/bin/env bash
export SERVICE_PATH="$(dirname "${BASH_SOURCE[0]}")"

SERVICE_NAME="matricula-ms"
PID_PATH_NAME="${SERVICE_PATH}/pid"
SYMLINK_PID_PATH_NAME="/tmp/${SERVICE_NAME}-pid"

if [ -f "${PID_PATH_NAME}" ]; then
    PID=$(cat "${PID_PATH_NAME}")
    echo "Stopping ${SERVICE_NAME} ..."
    kill -9 $PID
    echo "${SERVICE_NAME} stopped ..."
    if [ -L "${SYMLINK_PID_PATH_NAME}" ] || [ -e "${SYMLINK_PID_PATH_NAME}" ]; then
        rm "${SYMLINK_PID_PATH_NAME}"
    fi
fi
