@echo off

if "%OS%" == "Windows_NT" setlocal

echo.
echo #############################当前工作环境变量###########################
rem 设置工作目录
set "WORKING_DIR=%cd%"
echo WORKING_DIR=%WORKING_DIR%

rem 设置java
set "JAVA_HOME=D:\Works\deploy\jdk1.7.0_79"
echo JAVA_HOME=%JAVA_HOME%

rem 设置ant
set "ANT_HOME=D:\Works\deploy\apache-ant-1.9.4"
echo ANT_HOME=%ANT_HOME%

rem 设置tomcat
set "TOMCAT_HOME=D:\Works\deploy\apache-tomcat-7.0.62"
echo TOMCAT_HOME=%TOMCAT_HOME%

rem 设置svn
set "SVN_HOME=C:\Program Files\TortoiseSVN"
echo SVN_HOME=%SVN_HOME%

echo ########################################################################
echo %date%%time% 请检查上述环境变量是否符合您本机的情况
echo ########################################################################

echo 准备更新工作目录..................................................
set "EXESVN=%SVN_HOME%\bin\svn.exe"

if exist "%EXESVN%" goto okSVN
echo Cannot find "%EXESVN%"
echo This file is needed to run this program
goto end

:okSVN

echo 正在更新工作目录..................................................
call "%EXESVN%" update %WORKING_DIR%

echo 正在准备打包工作..................................................
set "EXECUTABLE=%ANT_HOME%\bin\ant.bat"
if exist "%EXECUTABLE%" goto okExec
echo Cannot find "%EXECUTABLE%"
echo This file is needed to run this program
goto end

:okExec
echo 正在进行打包工作..................................................
call "%EXECUTABLE%" war.onlinet

:end
@pause