@echo off
REM valiables
REM VersionOne server file name (required 11.0.0.828 or higher)
set VERSIONONE_FILE_NAME=VersionOne.Setup.exe

REM Sql base name
set DATABASE_NAME=TMPV1SDKTests

REM Sql backup file name
set BACKUP_FILE_NAME=%~dp0V1SDKTests.bak

REM Sql data dir
set SQL_DATA_DIR=C:\\Program Files\\Microsoft SQL Server\\MSSQL.1\\MSSQL\\Data

REM Server name
set SERVER_NAME=INTEGSRV01

REM Sql server name
set SQL_SEVER_NAME=(local)

REM IIS WebSite ID
set WEBSITE_ID=1

REM test website root path name
set TEST_WEBSITE_ROOT=IIS://localhost/W3SVC/%WEBSITE_ID%/ROOT %DATABASE_NAME%

echo "Database restoring..."

call osql -E -Q "RESTORE DATABASE %DATABASE_NAME% FROM DISK = '%BACKUP_FILE_NAME%' WITH FILE = 1,  NOUNLOAD,  REPLACE,  STATS = 10, MOVE 'demo.data.r63' TO '%SQL_DATA_DIR%\%DATABASE_NAME%.mdf',  MOVE 'demo.data.r63_log' TO '%SQL_DATA_DIR%\%DATABASE_NAME%_log.mdf'

echo "VersionOne installing..."

call %VERSIONONE_FILE_NAME% -Quiet:2 -DbServer:%SQL_SEVER_NAME% -WebSiteRootPath:%TEST_WEBSITE_ROOT%

pause;
