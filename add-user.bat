@echo off
rem Adds an application user with role "user" to the local WildFly installation (ApplicationRealm).
rem Usage: add-user.bat <user> <password>
setlocal

if "%~1"=="" goto usage
if "%~2"=="" goto usage
if not "%~3"=="" goto usage

rem Locate the WildFly installation unpacked by the Maven build
set "JBOSS_HOME="
for /d %%D in ("%~dp0server\wildfly-*") do set "JBOSS_HOME=%%~fD"

if not defined JBOSS_HOME goto notfound
if not exist "%JBOSS_HOME%\bin\add-user.bat" goto notfound

call "%JBOSS_HOME%\bin\add-user.bat" -a -u "%~1" -p "%~2" -g user
exit /b %ERRORLEVEL%

:usage
echo Usage: %~nx0 ^<user^> ^<password^> 1>&2
exit /b 1

:notfound
echo No WildFly installation found in %~dp0server, run the Maven build first. 1>&2
exit /b 1
