For Mac and Linux:

javac -cp "src/mysql-connector-j-9.3.0.jar" -d bin src/Main.java
java -cp "bin:src/mysql-connector-j-9.3.0.jar" Main

For Windows:

javac -cp "src;src/mysql-connector-j-9.3.0.jar" -d bin src/Main.java
java -cp "bin;src/mysql-connector-j-9.3.0.jar" Main
