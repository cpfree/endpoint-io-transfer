@echo off

set appJar="remote-tool.jar"

java -cp %appJar% cn.cpf.app.main.ShowFrameWithBdmpForClipboard -r 920 -px 2 -m 5 -p2 8
pause

