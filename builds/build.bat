@echo off

setlocal

set DELIVERABLE=%1

if "%DELIVERABLE%"=="" (
  set /p DELIVERABLE=please input build name£º
)

pushd .
cd %DELIVERABLE%
call mvn eclipse:clean
call mvn -npu -U eclipse:eclipse -Dwtpversion=1.0 -DdownloadSources=true
popd

pause

endlocal