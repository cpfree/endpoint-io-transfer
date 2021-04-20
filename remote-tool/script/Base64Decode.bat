@echo off

set appJar="remote-tool.jar"
java -cp %appJar% cn.cpf.app.main.Base64Decode -f %1
pause
