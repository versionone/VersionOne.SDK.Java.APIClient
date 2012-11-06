@ECHO OFF
REM # Batch file for changing IIS 7's ISAPI CGI Restrictions
SET DOT_NET_4_PATH=C:\Windows\Microsoft.NET\
SET DOT_NET_4_VERSION=v4.0.30319
SET DOT_NET_4_32BIT=%DOT_NET_4_PATH%\Framework\%DOT_NET_4_VERSION%
SET DOT_NET_4_64BIT=%DOT_NET_4_PATH%\Framework64\%DOT_NET_4_VERSION%

REM # Remove restriction for ASP.NET 4.0 (32-bit)
appcmd.exe set config -section:system.webServer/security/isapiCgiRestriction /-"[path='%DOT_NET_4_32BIT%\aspnet_isapi.dll']" /commit:apphost

REM # Remove restriction for ASP.NET 4.0 (64-bit)
appcmd.exe set config -section:system.webServer/security/isapiCgiRestriction /-"[path='%DOT_NET_4_64BIT%\aspnet_isapi.dll']" /commit:apphost

REM # Add restriction for ASP.NET 4.0 (32-bit) allowing ISAPI
appcmd.exe set config -section:system.webServer/security/isapiCgiRestriction /+"[path='%DOT_NET_4_32BIT%\aspnet_isapi.dll',allowed='True',groupId='ASP.NET %DOT_NET_4_VERSION%',description='ASP.NET %DOT_NET_4_VERSION%']" /commit:apphost

REM # Add restriction for ASP.NET 4.0 (64-bit) allowing ISAPI
appcmd.exe set config -section:system.webServer/security/isapiCgiRestriction /+"[path='%DOT_NET_4_64BIT%\aspnet_isapi.dll',allowed='True',groupId='ASP.NET %DOT_NET_4_VERSION%',description='ASP.NET %DOT_NET_4_VERSION%']" /commit:apphost
