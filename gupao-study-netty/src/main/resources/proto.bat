@echo off
set "CURRENT_DIR= %cd%"
if exist %CURRENT_DIR%\protoc.exe (
    protoc.exe proto/*.proto --java_out=../java/
    echo execute finished!
) else (
    echo protoc.exe file is not exist!
)
