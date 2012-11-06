SETLOCAL
SET BACKUP_FILE=V1SDKTests.bak
SET DB_NAME=V1SDKTests
SET INSTANCE_NAME=V1SDKTests
SET SQL_DATA_DIR=C:\Program Files (x86)\Microsoft SQL Server\MSSQL.1\MSSQL\DATA

ECHO Removing previous istallation
CALL VersionOne.Setup-Ultimate.exe -DeleteDatabase -Quiet:2 -u %INSTANCE_NAME%

ECHO Restoring database...
CALL osql -E -Q "RESTORE DATABASE [%DB_NAME%] FROM DISK = N'%1\%BACKUP_FILE%' WITH RECOVERY, REPLACE, MOVE N'demo.data.r63' TO N'%SQL_DATA_DIR%\V1SDKTests_Data0.mdf', MOVE N'demo.data.r63_log' TO N'%SQL_DATA_DIR%\V1SDKTests_Log1.ldf'"

ECHO Installing VersionOne...
CALL VersionOne.Setup-Ultimate.exe -Quiet:2 %INSTANCE_NAME% DbServer:(local) -DBName:%DB_NAME%

ENDLOCAL