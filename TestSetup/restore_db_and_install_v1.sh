#!/bin/bash

set -x
set -e

DB_NAME=$BUILD_TAG
INSTANCE_NAME=$BUILD_TAG

SQLCMD=sqlcmd
COMPRESSED_BACKUP_EXE=V1SDKTests_sql_selfextract.exe
BACKUP_FILE=V1SDKTests.sql
V1_SETUP=VersionOne.Setup.exe
RESTORE_DB=true
DB_SERVER="(local)"

cd $WORKSPACE/TestSetup
pwd

if [ $RESTORE_DB == "true" ]
then
    echo "Unpacking database backup from self-extracting archive"
    ./$COMPRESSED_BACKUP_EXE -y
    echo "Restoring database..."
    $SQLCMD -S $DB_SERVER -E -Q "DROP DATABASE [$DB_NAME]" >restore_db_and_install_v1.log
    $SQLCMD -S $DB_SERVER -E -Q "CREATE DATABASE [$DB_NAME]" >>restore_db_and_install_v1.log
    $SQLCMD -S $DB_SERVER -d $DB_NAME -E -i $BACKUP_FILE >>restore_db_and_install_v1.log
    echo "Removing DB Backup file"
fi

echo "Installing VersionOne at http://localhost/${INSTANCE_NAME} associated with DB Server: ${DB_SERVER} and DB Name: ${DB_NAME}..."

./$V1_SETUP -Quiet:2 -DbServer:$DB_SERVER -LogFile:$WORKSPACE\\TestSetup\\setup.log -DBName:$DB_NAME $INSTANCE_NAME

if [ $? -gt 0 ]
then 
  tail -50 $WORKSPACE/TestSetup/setup.log | grep Exception
else
    echo "Thank you, you can browse to http://localhost/$INSTANCE_NAME and login with admin / admin, and you should be able to execute the integration tests now..."
    
fi


