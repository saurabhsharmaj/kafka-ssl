
echo "Enter User Name:"
read userName

echo "Enter password:"
read password

../bin/kafka-configs.sh --zookeeper localhost:2184 --zk-tls-config-file ../config/zookeeper-client.properties --entity-type users --entity-name $userName --alter --add-config SCRAM-SHA-512=[password=$password]
