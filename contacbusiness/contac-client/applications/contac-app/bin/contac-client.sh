#!/bin/bash

# Este SCRIPT Ejecuta el cliente WS del Catalogo de Bienes y Servicios (CBS) SISCAE en plataformas Unix

DIRNAME=`dirname $0`
PROGNAME=`basename $0`

# OS specific support (must be 'true' or 'false').
cygwin=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;
esac

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
    [ -n "$JAVA_HOME" ] &&
        JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi

# Setup SISCAE_HOME

# get the full path (without any relative bits)
SISCAE_HOME=`cd $DIRNAME/..; pwd`

export SISCAE_HOME

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
	JAVA="$JAVA_HOME/bin/java"
    else
	JAVA="java"
    fi
fi

#Setup SISCAE_CLASSPATH
SISCAE_CLASSPATH=

for i in $SISCAE_HOME/lib/*.jar; do
  SISCAE_CLASSPATH="$SISCAE_CLASSPATH":"$i"
done

#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/catalogo-cmdline-1.3.1-SNAPSHOT.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/seca-ws-1.3.1-SNAPSHOT.jar

#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/activation.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/mail.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/log4j.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/javassist.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jaxb-api.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jaxb-impl.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/stax-api.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jbossws-common.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jbossws-client.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jbossws-spi.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-jaxws.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-jaxrpc.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-saaj.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-xml-binding.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/wsdl4j.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/policy.jar

# taken from SISCAEall-client.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-logging-spi.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-common-core.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-common-client.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/concurrent.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/commons-logging.jar
#SISCAE_CLASSPATH=$SISCAE_CLASSPATH:$SISCAE_HOME/lib/jboss-remoting.jar



# Setup SISCAE sepecific properties
JAVA_OPTS="$JAVA_OPTS"

# Setup the java endorsed dirs
SISCAE_ENDORSED_DIRS="$SISCAE_HOME/lib/endorsed"

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
    SISCAE_HOME=`cygpath --path --windows "$SISCAE_HOME"`
    JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
    SISCAE_CLASSPATH=`cygpath --path --windows "$SISCAE_CLASSPATH"`
    SISCAE_ENDORSED_DIRS=`cygpath --path --windows "$SISCAE_ENDORSED_DIRS"`
fi

# Execute the JVM
eval "$JAVA" $JAVA_OPTS \
   -Djava.endorsed.dirs="$SISCAE_ENDORSED_DIRS" \
   -Dlog4j.configuration=./log4j.properties \
   -classpath "$SISCAE_CLASSPATH" \
   ni.gob.seca.apps.cmdline.catalogo.Main \
   "$@"
