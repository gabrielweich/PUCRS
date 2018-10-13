@echo off

set py=
for %%a in (python.exe) do set py=%%~$PATH:a
if "%py%"=="" (
		echo Python n∆o encontrado no PATH.
		goto _Fim
)

for /r "%~dp0" %%a in (files\*) do (
	echo %%~nxa
	::type "%%~a"
	python roundrobin.py "%%~a"
	echo:
)

:_Fim
pause