@echo off
P:
cd P:\git\git-app\endpoint-io-transfer\out\artifacts\endpoint_io_transfer_jar\
set appJar="endpoint-io-transfer.jar"
set savePath=E:\res\FMBS_DEV_BANK\inner\clipout\

:stat

echo   : exit
echo 1 : BdmpDistinguishForMainScreenShot
echo 2 : open savePath %savePath%
echo 3 : clear

:judge

set /p a=
if "%a%"==""  exit
if "%a%"=="1" goto exec
if "%a%"=="2" start %savePath%
if "%a%"=="3" clear
goto stat

:exec
java -cp %appJar% cn.cpf.app.main.BdmpDistinguishForMainScreenShot -p %savePath%
goto stat

