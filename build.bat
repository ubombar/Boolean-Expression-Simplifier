echo off
cls
REM echo [Compilation Log:] Compiling the java program using "javac src\\*.java -d bin"
javac src\\*.java -d bin
REM echo [Compilation Log:] Running Program:
java -cp bin Main