# endpoint-io-transfer

终端io传输工具, 方便远程终端设备的输入输出

- 输入: 先将文件转换为Base64编码,再通过文本打印功能将Base64编码输入至远程, 在远程再将Base64编码转换为文件
- 输出: 远程通过 remote-tool 将数据 转化为 bdmp 图片在屏幕上进行显示, 之后本地 endpoint-io-transfer 程序通过识别屏幕显示的图片, 将图片还原成文件.

> bdmp 图片详细请查看
> - <https://github.com/cosycode/bit-data-map>
> - <https://gitee.com/cosycode/bit-data-map>

## 功能介绍

1. 文本打印功能
   

## 环境准备

1. 该程序是使用Java1.8编写的jar程序, 请在本机和远程均安装并配置好JRE1.8运行环境.
2. 本地 获取 endpoint-io-transfer 执行程序.
3. 远程 上传 remote-tool 执行程序.
   > 如果没有上传渠道, 可以使用 endpoint-io-transfer 的 **文本打印功能** 上传文件
4. 


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
