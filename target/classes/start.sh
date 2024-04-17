#!/usr/bin/env bash
export SERVICE_PATH="$(dirname "${BASH_SOURCE[0]}")"

SERVICE_NAME="matricula-ms"
PID=0
FILE="MatricuLAR-0.0.1-SNAPSHOT"
JAR_FILE="${FILE}.jar"
VM_MEMORY="128m"
PID_FILE_NAME="pid"
LOG_DIR="${SERVICE_PATH}/logs"
#LOGROTATE="logrotate.d"
#LOGROTATE_DIR="${SERVICE_PATH}/${LOGROTATE}"
#LOGROTATE_CONFIG_LINK="/etc/${LOGROTATE}/${FILE}"
SPRING_LOG_FILE="/tmp/spring.log"
ON_OUT_OF_MEMORY_ERROR_MESSAGE="OnOutOfMemoryError na inicialização do serviço de matricula, considere aumentar os valores das diretiras: \n-Xms e -Xmx (variável VM_MEMORY do script start.sh)"
SYMLINK_PID_PATH_NAME="/tmp/${SERVICE_NAME}-pid"
SYSTEMD_SERVICE_PATH='/usr/local/bin'
SYSTEMD_CONFIG_SERVICE_PATH='/etc/systemd/system'

if [ -f "${SERVICE_PATH}/${PID_FILE_NAME}" ]; then
    PID=$(cat "${SERVICE_PATH}/${PID_FILE_NAME}")
fi

if [[ ! -d "${LOG_DIR}" ]]; then
    mkdir -p "${LOG_DIR}"
fi

if [[ -f "${SPRING_LOG_FILE}" ]]; then
    rm "${SPRING_LOG_FILE}"
fi

if { ps -p $PID -o pid=,tty=,time=,cmd=; } 2>/dev/null; then
    echo "${SERVICE_NAME} is already running"
else
    nohup java -jar -server -Xms$VM_MEMORY -Xmx$VM_MEMORY -XX:+UseZGC -XX:MaxGCPauseMillis=300 -XX:ParallelGCThreads=20 -XX:InitiatingHeapOccupancyPercent=60 -XX:+ParallelRefProcEnabled -XX:OnOutOfMemoryError="echo ${ON_OUT_OF_MEMORY_ERROR_MESSAGE} > ${LOG_DIR}/errors.log && sudo ${SERVICE_PATH}/stop" -XX:+OptimizeStringConcat "${SERVICE_PATH}/${JAR_FILE}" > "${LOG_DIR}/service.log" 2> "${LOG_DIR}/errors.log" < /dev/null & PID=$!; echo $PID >"${SERVICE_PATH}/${PID_FILE_NAME}"

    chmod -R 644 "${LOG_DIR}"
    chown -R root. "${LOG_DIR}"

    ln -sf "${SERVICE_PATH}/${PID_FILE_NAME}" "${SYMLINK_PID_PATH_NAME}"
fi

#if [[ -d "${LOGROTATE_DIR}" ]]; then
#    chmod -R 644 "${LOGROTATE_DIR}"
#    chown -R root. "${LOGROTATE_DIR}"
#    ln -sf "${LOGROTATE_DIR}/${FILE}" "${LOGROTATE_CONFIG_LINK}"
#fi
