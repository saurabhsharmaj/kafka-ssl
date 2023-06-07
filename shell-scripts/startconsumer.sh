
echo "Enter Created Topic-Name:"
read tpname

../bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic $tpname --consumer.config ../config/consumer.properties --from-beginning
