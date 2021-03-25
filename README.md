# endpoint-io-transfer

终端io传输工具, 方便远程终端设备的输入输出

## 使用方式

### 一, 系统环境准备

1. 首先, 该程序是使用Java1.8编写的, 请在本机和远程均安装并配置好JRE1.8运行环境.

### 二, 将remote-tool传输至远程终端

如果可以直接往往远程拷贝文件的话, 则就按你的方式拷贝文件, 然后就可以跳过此步骤!

如果无法直接往远程拷贝文件, 则利用该程序的 文本打印功能 将小文件传到远程.

> 文本打印功能是利用按键输入的方式, 一个字符一个字符的将文件的Base64位编码传输到远程, 到远程之后再转换为文件

> (remote-tool.jar文件大概只有20Kb, 两到三分钟应该就可以传完了).

传输至远程后再将Base64编码转换为文件

### 三, remote-tool 命令

remote-tool的功能有

1. 将Base64编码文本文件转换为文件
   
    command: `java -cp remote-tool.jar cn.cpf.man.Base64Decode -f ${base64位编码文件路径}`

2. 将剪贴板中的文本渲染成 bit-data-map 图片. 并显示

    command: `java -cp remote-tool.jar cn.cpf.man.ShowFrameWithBdmpForClipboard -r 800 -px 2 -m 20`

3. 如果参数路径指向文件, 则将文件转换为 bit-data-map 并显示, 如果参数是文件夹, 则遍历文件夹里面的文件, 并按照配置的时间间隔循环显示.

    command: `java -cp remote-tool.jar cn.cpf.man.ShowFrameWithPixelPngForFile -p ${文件或文件夹路径} -r 800 -px 2 -m 20 -inv 2000 -maxLen 300000`

2. 安装好JDK之后, 启动运行 endpoint-io-transfer.jar, 测试 文本打印功能, 详情请看文本打印功能.
   
3. 








