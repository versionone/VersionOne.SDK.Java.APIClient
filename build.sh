#!/bin/bash
BINS=`ls VersionOne.SDK.Java.APIClient-*[0-9].[0-9].[0-9].jar`
SOURCES=`ls VersionOne.SDK.Java.APIClient -*[0-9].[0-9].[0-9]-sources.jar`
JAVADOCS=`ls VersionOne.SDK.Java.APIClient -*[0-9].[0-9].[0-9]-javadoc.jar`

mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -s settings.xml -DrepositoryId=sonatype-nexus-staging -DpomFile=pom.xml -Dfile=$BINS -X && \ 
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -s settings.xml -DrepositoryId=sonatype-nexus-staging -DpomFile=pom.xml -Dfile=$SOURCES -Dclassifier=sources -X && \
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -s settings.xml -DrepositoryId=sonatype-nexus-staging -DpomFile=pom.xml -Dfile=$JAVADOCS -Dclassifier=javadoc -X 
