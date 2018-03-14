#!/bin/sh
pwd=`pwd`
projects=(game-context game-internal-service game-service game-protocol game-poker-ai game-landlord)
for p in  ${projects[*]}
do
	cd $pwd/$p
#	svn ci pom.xml
	#mvn clean
	#svn update src
	mvn -Dmaven.test.skip=true clean install
done
cd $pwd
