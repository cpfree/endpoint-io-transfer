@echo off

set appJar="remote-tool.jar"

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
java -cp %appJar% cn.cpf.app.main.BdmpRecFromFile
goto stat

