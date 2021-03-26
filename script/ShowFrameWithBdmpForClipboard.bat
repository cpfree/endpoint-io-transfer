@echo off
P:
cd P:\git\git-app\endpoint-io-transfer\out\artifacts\endpoint_io_transfer_jar\
set appJar="endpoint-io-transfer.jar"

java -cp %appJar% cn.cpf.app.main.ShowFrameWithBdmpForClipboard -r 920 -px 2 -m 5 -p2 8
pause

