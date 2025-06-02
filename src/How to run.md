For Mac and Linux:
javac -cp "src/mysql-connector-j-9.3.0.jar" -d bin $(find src -name "\*.java") && java -cp "bin:src/mysql-connector-j-9.3.0.jar" Main

For Windows:
mkdir bin
javac -cp "src;src/mysql-connector-j-9.3.0.jar" -d bin src\Main.java src\db\GameDB.java src\ui\GameUI.java src\models\User.java src\models\PlayerData.java
java -cp "bin;src/mysql-connector-j-9.3.0.jar" Main
