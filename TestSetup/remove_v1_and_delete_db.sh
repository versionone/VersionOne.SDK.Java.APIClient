#!/bin/bash

set -x
set -e

INSTANCENAME=$BUILD_TAG
V1_SETUP=VersionOne.Setup.exe


cd $WORKSPACE/TestSetup
pwd


./$V1_SETUP -DeleteDatabase -LogFile:$WORKSPACE\TestSetup\setup.log -Quiet:2 -u $INSTANCENAME


