#export KAFKA_OPTS="-Djava.security.auth.login.config=/opt/appops/kafka_2.12-3.3.1/config/kafka_server_jaas.conf"

../bin/kafka-server-start.sh -daemon ../config/server.properties
