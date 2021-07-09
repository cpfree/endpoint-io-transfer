@echo off

set appJar="remote-tool.jar"
echo path: %1

java -cp %appJar% cn.cpf.man.ShowFrameWithBdmpForFile -p %1 -r 920 -px 2 -m 5 -p2 8 -inv 1000 -maxlen 409600
pause
