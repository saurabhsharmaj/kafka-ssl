
echo "Enter User Name:"
read username

echo "Enter Created Topic-Name:"
read tpname

echo "Enter Operation Name:"
read opname

./bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2184 --zk-tls-config-file ./config/zookeeper-client.properties --add --allow-principal User:$username --topic $tpname  --operation $opname

