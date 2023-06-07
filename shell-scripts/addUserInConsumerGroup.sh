
echo "Enter User Name:" 
read username
echo "Enter Group Name:" 
read groupname
echo "Enter Operation Name:" 
read opname

../bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2184 --zk-tls-config-file ../config/zookeeper-client.properties --add --allow-principal User:$username --group $groupname --operation $opname
