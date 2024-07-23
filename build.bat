@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%JAVA_HOME%\bin;%PATH%

@rem alright make a gradle task that compiles and links against jni, then run these two commands before it does that using preBuildTask
@rem when you run gradle wrapper, it should make a gradlew for linux/macos and then gradlew.bat for windows
@rem javac -h . HelloJNI.java
@rem gcc -std=c17 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o hello.dll HelloJNI.c
@rem x86_64-w64-mingw32-gcc -std=c17 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o hello.dll HelloJNI.c
@rem cl /I "%JAVA_HOME%\include" /I "%JAVA_HOME%\include\win32" /LD HelloJNI.c /link /OUT:hello.dll
@rem [MSVC](https://visualstudio.microsoft.com/downloads/#microsoft-visual-c-redistributable-for-visual-studio-2022)

javac PicForge.java
java PicForge

del *.class

if %errorlevel% neq 0 (
    echo Compilation failed!
) else (
    echo Compilation finished successfully!
)
pause