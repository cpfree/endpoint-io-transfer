@echo off

for %%r in (%*) do (
echo "========================\r\n\n"
echo %%r
java -cp D:\project\my-pro\out\artifacts\my_pro_jar\my-pro.jar cn.cpf.man.BdmpGenerate %%r 1 800
)
pause
