@echo off

if "%OS%" == "Windows_NT" setlocal

echo.
echo #############################��ǰ������������###########################
rem ���ù���Ŀ¼
set "WORKING_DIR=%cd%"
echo WORKING_DIR=%WORKING_DIR%

rem ����java
set "JAVA_HOME=D:\Works\deploy\jdk1.7.0_79"
echo JAVA_HOME=%JAVA_HOME%

rem ����ant
set "ANT_HOME=D:\Works\deploy\apache-ant-1.9.4"
echo ANT_HOME=%ANT_HOME%

rem ����tomcat
set "TOMCAT_HOME=D:\Works\deploy\apache-tomcat-7.0.62"
echo TOMCAT_HOME=%TOMCAT_HOME%

rem ����svn
set "SVN_HOME=C:\Program Files\TortoiseSVN"
echo SVN_HOME=%SVN_HOME%

echo ########################################################################
echo %date%%time% �����������������Ƿ���������������
echo ########################################################################

echo ׼�����¹���Ŀ¼..................................................
set "EXESVN=%SVN_HOME%\bin\svn.exe"

if exist "%EXESVN%" goto okSVN
echo Cannot find "%EXESVN%"
echo This file is needed to run this program
goto end

:okSVN

echo ���ڸ��¹���Ŀ¼..................................................
call "%EXESVN%" update %WORKING_DIR%

echo ����׼���������..................................................
set "EXECUTABLE=%ANT_HOME%\bin\ant.bat"
if exist "%EXECUTABLE%" goto okExec
echo Cannot find "%EXECUTABLE%"
echo This file is needed to run this program
goto end

:okExec
echo ���ڽ��д������..................................................
call "%EXECUTABLE%" war.onlinet

:end
@pause