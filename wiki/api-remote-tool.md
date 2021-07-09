# remote-tool

remote-tool.jar 是一个可执行jar, 其功能如下

1. 将Base64编码文本文件转换为文件
   
   command: `java -cp remote-tool.jar cn.cpf.man.Base64Decode -f ${base64位编码文件路径}`

   ```conf
   f: 待转换的Base64编码文件路径
   d: 转换文件的保存位置, 默认将文件保存至当前文本文件夹
   ```

2. 将剪贴板中的文本渲染成 bit-data-map 图片. 并显示

    command: `java -cp remote-tool.jar cn.cpf.man.ShowFrameWithBdmpForClipboard -r 800 -px 2 -m 20`

   ```conf
   r : 生成的图片一行渲染多少个数据点
   px: 每个数据点的边长
   m : 生成的图片边缘宽度
   p2: 生成的每个数据点携带几个bit数据(默认为8)
   ```

3. 将文件转换为bit-data-map

   ```conf
   f 待转换为bit-data-map的源文件路径(不能为空)
   d : 生成的图片保存路径
   r : 生成的图片一行渲染多少个数据点
   px: 每个数据点的边长
   m : 生成的图片边缘宽度
   p2: 生成的每个数据点携带几个bit数据(默认为8)
   inv: 如果路径是文件夹, 文件夹里面有多张图片, 渲染每张图片的时间间隔
   maxLen: 如果路径是文件夹, 渲染的文件最大大小, 默认最大为400K, 如果文件大小超过400K, 则跳过渲染
   ```

4. 如果参数路径指向文件, 则将文件转换为 bit-data-map 并显示, 如果参数是文件夹, 则遍历文件夹里面的文件, 并按照配置的时间间隔循环显示.

    command: `java -cp remote-tool.jar cn.cpf.man.ShowFrameWithBdmpForFile -p ${文件或文件夹路径} -r 800 -px 2 -m 20 -inv 2000 -maxLen 300000`

   ```conf
   p 文件或文件夹路径
   r : 生成的图片一行渲染多少个数据点
   px: 每个数据点的边长
   m : 生成的图片边缘宽度
   p2: 生成的每个数据点携带几个bit数据(默认为8)
   inv: 如果路径是文件夹, 文件夹里面有多张图片, 渲染每张图片的时间间隔
   maxLen: 如果路径是文件夹, 渲染的文件最大大小, 默认最大为400K, 如果文件大小超过400K, 则跳过渲染
   ```
