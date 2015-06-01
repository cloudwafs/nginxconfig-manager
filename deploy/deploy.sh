#!/bin/bash
date
usage(){
    echo "  Usage:"
    echo "     use '$0 dev/qa'."
    exit 1
}

ENV=$1
cd `dirname "$0"`
PRGDIR=`pwd`

if [ "$ENV" != "qa" -a "$ENV" != "dev" ]; then
  echo "Your input is not corrent!"
  usage
  exit 1
fi

#svn co
cd /tmp
rm -rf loadbalance_manager
svn co svn://svn.dy/platform/loadbalance-manager/branches/develop loadbalance-manager
cd loadbalance-manager

#maven 
MVN_PROFILE="-P${ENV}"
mvn clean package -DskipTests $MVN_PROFILE

WEB_HOST="172.20.0.160"
USER="ops"
PASSWORD="ops@123"
DATE=$(date +%Y%m%d_%H%M%S)
VERSION=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`
echo "version is $VERSION"

#if [ "$ENV" == "dev" ]; then
#  WEB_HOST="172.20.0.160"
#  USER="ops"
#  PASSWORD="ops@123"
#fi

cd $PRGDIR
echo "=============== deploying web to $WEB_HOST ============="
./deploy-web.sh $WEB_HOST $USER $PASSWORD $DATE $VERSION
#go back
cd $PRGDIR