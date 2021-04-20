@echo off

set appJar="remote-tool.jar"

for %%r in (%*) do (
echo "========================\r\n\n"
echo %%r
java -cp %appJar% cn.cpf.app.main.BdmpGeneForFile -f %%r -px 1 -r 800 -px 8 -m 10
)
pause
