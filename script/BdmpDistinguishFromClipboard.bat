@echo off
set appJar="P:\git\git-app\endpoint-io-transfer\out\artifacts\endpoint_io_transfer_jar\endpoint-io-transfer.jar"
java -cp %appJar% cn.cpf.app.main.BdmpDistinguishFromClipboard
pause
