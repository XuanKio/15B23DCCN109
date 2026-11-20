@echo off
chcp 65001 >nul
cd /d "%~dp0"
rem Chạy CineMan9: build bằng Maven, tự tải Tomcat 10.1 và mở trình duyệt.
if not exist src\main\resources\db.properties copy src\main\resources\db.properties.example src\main\resources\db.properties >nul
echo Dang build va khoi dong CineMan9, lan dau se tai Tomcat nen hoi lau...
echo Trang web: http://localhost:8080/CineMan9/   (dong cua so nay de tat)
start "" powershell -NoProfile -WindowStyle Hidden -Command "for($i=0;$i -lt 300;$i++){try{if((Invoke-WebRequest -UseBasicParsing http://localhost:8080/CineMan9/ -TimeoutSec 2).StatusCode -eq 200){Start-Process http://localhost:8080/CineMan9/;break}}catch{};Start-Sleep 1}"
call mvn -q package cargo:run
pause
