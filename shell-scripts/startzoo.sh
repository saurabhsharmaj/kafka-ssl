#export KAFKA_OPTS="-Djava.security.auth.login.config=/opt/appops/kafka_2.12-3.3.1/config/zookeeper_jaas.conf"

../bin/zookeeper-server-start.sh -daemon ../config/zookeeper.properties
