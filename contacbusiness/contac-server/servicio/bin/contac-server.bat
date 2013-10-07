@echo off

rem $Id: wsprovide.bat 2325 2007-02-09 22:14:15Z jason.greene@jboss.com $

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=contac-client.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%


set ARGS=
:loop
if [%1] == [] goto endloop
    set ARGS=%ARGS% %1 
    shift
    goto loop
:endloop


set JAVA=%JAVA_HOME%\bin\java
set SISCAE_HOME=%DIRNAME%\..
rem Setup the java endorsed dirs
set SISCAE_ENDORSED_DIRS=..\lib\endorsed

rem Setup the wstools classpath

set SISCAE_CLASSPATH=

set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\catalogo-cmdline-1.3.1-SNAPSHOT.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\seca-ws-1.3.1-SNAPSHOT.jar

set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\activation.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\mail.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\log4j.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\javassist.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jaxb-api.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jaxb-impl.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\stax-api.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jbossws-common.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jbossws-client.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jbossws-spi.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-jaxws.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-jaxrpc.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-saaj.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-xml-binding.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\wsdl4j.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\policy.jar

# taken from SISCAEall-client.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-logging-spi.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-common-core.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-common-client.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\concurrent.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\commons-logging.jar
set SISCAE_CLASSPATH=%SISCAE_CLASSPATH%;%SISCAE_HOME%\lib\jboss-remoting.jar

echo "%JAVA%" %JAVA_OPTS% -Djava.endorsed.dirs="%SISCAE_ENDORSED_DIRS%" -Dlog4j.configuration=wstools-log4j.xml ni.gob.seca.apps.cmdline.catalogo.Main %ARGS%

set CLASSPATH=%SISCAE_CLASSPATH%

rem Execute the JVM
"%JAVA%" %JAVA_OPTS% -Djava.endorsed.dirs="%SISCAE_ENDORSED_DIRS%" -Dlog4j.configuration=wstools-log4j.xml ni.gob.seca.apps.cmdline.catalogo.Main %ARGS%
:EOF
