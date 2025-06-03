@echo off
setlocal EnableDelayedExpansion

if not exist bin mkdir bin

set "MYSQL_JAR="
for %%f in (src\mysql-connector-j-*.jar) do (
    set "MYSQL_JAR=%%f"
    goto :found
)

echo ❌ MySQL Connector JAR not found in src\
exit /b 1

:found
echo 📦 Found MySQL Connector: %MYSQL_JAR%

set "JAVA_FILES="
for /R src %%f in (*.java) do (
    set "JAVA_FILES=!JAVA_FILES! %%f"
)

echo 📦 Compiling Java files...
javac -cp "src;%MYSQL_JAR%" -d bin %JAVA_FILES%

echo 🚀 Running Main class...
java -cp "bin;%MYSQL_JAR%" Main
