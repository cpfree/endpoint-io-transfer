@echo off
P:
cd P:\git\git-app\endpoint-io-transfer\out\artifacts\endpoint_io_transfer_jar\
set appJar="endpoint-io-transfer.jar"
java -cp %appJar% cn.cpf.app.main.Base64Encode -f %1
pause
