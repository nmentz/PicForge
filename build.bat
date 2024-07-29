@echo on

setlocal
echo updating registry paths and values
set "jarKey=HKEY_CLASSES_ROOT\.jar"
set "jarFileKey=HKEY_CLASSES_ROOT\jarfile"
set "jarFileCommandKey=HKEY_CLASSES_ROOT\jarfile\shell\open\command"
set "jarFileDefaultValue=jarfile"
set "javaExePath=C:\Program Files\Java\jdk-22\bin\javaw.exe"

echo Updating .jar file association...
reg add "%jarKey%" /ve /d "%jarFileDefaultValue%" /f

REM Create jarfile key if it doesn't exist
echo Creating jarfile key...
reg add "%jarFileKey%" /f

REM Set the command for jarfile to use javaw.exe
echo Setting jarfile command...
reg add "%jarFileCommandKey%" /ve /d "\"%javaExePath%\" -jar \"%%1\"" /f

REM Confirm the changes
echo Confirming the changes...
reg query "%jarKey%"
reg query "%jarFileCommandKey%"

echo File association for .jar files has been updated.
endlocal

REM Globally set the JAVA_HOME variable
SETX JAVA_HOME "C:\Program Files\Java\jdk-22"

REM Update the PATH environment variable to include JAVA_HOME\bin
REM Need to manually include the current PATH value
SET PATH=C:\Program Files\Java\jdk-22\bin;%PATH%

REM Associate .jar files with javaw.exe
assoc .jar=JarFile
ftype JarFile="%JAVA_HOME%\javaw.exe" "%%1"

REM Change to the directory of the batch file
cd /d %~dp0

REM Compile Java files
javac -d build src/java/*.java

REM Create META-INF directory and MANIFEST.MF
mkdir build\META-INF
echo Manifest-Version: 1.0 > build\META-INF\MANIFEST.MF
echo Main-Class: PicForge >> build\META-INF\MANIFEST.MF

REM Create the JAR file
jar cfm build/PicForge.jar build/META-INF/MANIFEST.MF -C build .

REM Clean build directory
del build\*.class 
del build\META-INF\MANIFEST.MF
rmdir build\META-INF

REM Copy JAR to desktop
copy build\PicForge.jar C:\Users\%USERNAME%\OneDrive\Desktop
copy build\PicForge.jar C:\Users\%USERNAME%\Desktop

REM Run the JAR file
REM java -jar build\PicForge.jar

if %errorlevel% neq 0 (
    echo Compilation failed!
) else (
    echo Compilation finished successfully!
)
pause

REM "%javaExePath%" -jar build/PicForge.jar