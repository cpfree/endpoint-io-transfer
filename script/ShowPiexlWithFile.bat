echo path: %1
set appJar=D:\project\my-pro\out\artifacts\my_pro_jar\my-pro.jar
java -cp %appJar% cn.cpf.man.ShowFrameWithPixelPngForFile -p %1 -r 920 -px 2 -m 5 -p2 8 -inv 1000 - maxlen 409600
pause
