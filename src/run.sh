#!/bin/bash


mkdir -p bin
MYSQL_JAR=$(find src -name "mysql-connector-j-*.jar" | head -n 1)

if [ -z "$MYSQL_JAR" ]; then
  echo "❌ MySQL Connector JAR not found in src/"
  exit 1
fi

echo "📦 Using MySQL Connector: $MYSQL_JAR"

javac -cp "$MYSQL_JAR" -d bin $(find src -name "*.java")

java -cp "bin:$MYSQL_JAR" Main
