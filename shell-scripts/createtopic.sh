
echo "Enter Topic Name:"
read topicName

echo "Enter replication-factor[1]:"
read replica

echo "Enter partition[1]:"
read partition

../bin/kafka-topics.sh --create  --bootstrap-server localhost:9093 --command-config ../config/topic-config.conf --replication-factor $replica --partitions $partition --topic $topicName
