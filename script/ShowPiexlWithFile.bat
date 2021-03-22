echo path: %1
set appJar=P:\git\git-app\endpoint-io-transfer\out\artifacts\romote_tool_jar\romote-tool.jar
java -cp %appJar% cn.cpf.man.ShowFrameWithPixelPngForFile -p %1 -r 920 -px 2 -m 5 -p2 8 -inv 1000 -maxlen 409600
pause
