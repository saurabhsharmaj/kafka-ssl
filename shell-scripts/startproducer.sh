#export KAFKA_OPTS="-Djava.security.auth.login.config=/opt/appops/kafka_2.12-3.3.1/config/kafka_server_jaas.conf"

echo "Enter Created Topic-Name:"
read tpname

../bin/kafka-console-producer.sh --broker-list localhost:9093 --topic $tpname --producer.config ../config/producer.properties
