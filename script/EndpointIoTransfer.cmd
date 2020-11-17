@echo off
set appJar="P:\git\git-app\endpoint-io-transfer\out\artifacts\endpoint_io_transfer_jar\endpoint-io-transfer.jar"

echo 0 : open jar
echo 1 : BdmpDistinguishFromClipboard
echo 2 : BdmpDistinguishForMainScreenShot

set /p a=

if "%a%"=="" java -cp %appJar% cn.cpf.app.main.BdmpDistinguishFromClipboard
if "%a%"=="1" java -cp %appJar% cn.cpf.app.main.BdmpDistinguishFromClipboard
if "%a%"=="2" java -cp %appJar% cn.cpf.app.main.BdmpDistinguishForMainScreenShot

pause
