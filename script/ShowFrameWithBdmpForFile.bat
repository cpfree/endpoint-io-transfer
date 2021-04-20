@echo off
P:
cd P:\git\git-app\endpoint-io-transfer\out\artifacts\endpoint_io_transfer_jar\
set appJar="endpoint-io-transfer.jar"
echo path: %1

java -cp %appJar% cn.cpf.app.main.ShowFrameWithBdmpForFile -p %1 -r 920 -px 2 -m 5 -p2 8 -inv 1000 -maxlen 409600
pause
