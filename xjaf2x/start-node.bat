@echo off

if "x%JBOSS_HOME%" == "x" (
  echo "ERROR: Environment variable JBOSS_HOME not set."
  goto END
)

if "x%JAVA_HOME%" == "x" (
  set JAVA_EXE=java
) else (
  set "JAVA_EXE=%JAVA_HOME%\bin\java"
)

set "JBOSS_MODULES_BASE=%JBOSS_HOME%\modules\system\layers\base"
set "JBOSS_BIN=%JBOSS_HOME%\bin"
set "CWD=%CD%"

"%JAVA_EXE%" -Dxjaf2x.base.dir="%CWD%" -cp .;"%JBOSS_MODULES_BASE%\org\infinispan\main\infinispan-core-5.2.6.Final-redhat-1.jar";"%JBOSS_MODULES_BASE%\org\jboss\as\cli\main\jboss-as-cli-7.2.0.Final-redhat-8.jar";"%JBOSS_MODULES_BASE%\org\jboss\as\controller-client\main\jboss-as-controller-client-7.2.0.Final-redhat-8.jar";"%JBOSS_MODULES_BASE%\org\jboss\as\naming\main\jboss-as-naming-7.2.0.Final-redhat-8.jar";"%JBOSS_MODULES_BASE%\org\jboss\ejb-client\main\jboss-ejb-client-1.0.21.Final-redhat-1.jar";"%JBOSS_MODULES_BASE%\org\jboss\ejb3\main\jboss-ejb3-ext-api-2.0.0-redhat-2.jar";"%JBOSS_MODULES_BASE%\org\jboss\as\jaxrs\main\jboss-as-jaxrs-7.2.0.Final-redhat-8.jar";"%JBOSS_MODULES_BASE%\org\jboss\msc\main\jboss-msc-1.0.4.GA-redhat-1.jar";"%JBOSS_MODULES_BASE%\org\jboss\as\process-controller\main\jboss-as-process-controller-7.2.0.Final-redhat-8.jar";"%JBOSS_BIN%\client\jboss-client.jar";"%JBOSS_BIN%\client\jboss-cli-client.jar";"%CWD%\xjaf2x.jar" xjaf2x.StartNode %*

:END
if "x%NOPAUSE%" == "x" pause
