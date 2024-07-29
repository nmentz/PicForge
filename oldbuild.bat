
@rem alright make a gradle task that compiles and links against jni, then run these two commands before it does that using preBuildTask
@rem when you run gradle wrapper, it should make a gradlew for linux/macos and then gradlew.bat for windows
@rem javac -h . HelloJNI.java
@rem gcc -std=c17 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o hello.dll HelloJNI.c
@rem x86_64-w64-mingw32-gcc -std=c17 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o hello.dll HelloJNI.c
@rem cl /I "%JAVA_HOME%\include" /I "%JAVA_HOME%\include\win32" /LD HelloJNI.c /link /OUT:hello.dll
@rem [MSVC](https://visualstudio.microsoft.com/downloads/#microsoft-visual-c-redistributable-for-visual-studio-2022)

@rem javac -h . src/java/Model.java
@rem echo "Main-Class: PicForge\n" >> build/manifest.txt 
@rem javac -d build src/java/*.java
@rem java -cp build PicForge
@rem jar cfe build/PicForge.jar build/manifest.txt PicForge -C build .

@rem you will need to install wix
@rem wix https://github.com/wixtoolset/wix3/releases

@rem you will also need to right click on your .jar file and associate it with C:\Program Files\Java\jdk-22\bin\javaw


@echo on
@rem globally set the JAVA_HOME variable
SETX JAVA_HOME "C:\Program Files\Java\jdk-22"
SETX PATH=%JAVA_HOME%\bin;%PATH%

@rem associate .jar files with jdk 22
assoc .jar=JarFile
ftype JarFile="%JAVA_HOME%\javaw.exe" "%%1"

cd /d %~dp0

@rem compile java
javac -d build src/java/*.java
mkdir build\META-INF
echo Manifest-Version: 1.0 > build/META-INF/MANIFEST.MF
echo Main-Class: PicForge >> build/META-INF/MANIFEST.MF
jar cfm build/PicForge.jar build/META-INF/MANIFEST.MF -C build .

@rem clean build dir
del build\*.class 
del build\META-INF\MANIFEST.MF
rmdir build\META-INF

@rem copy .jar to desktop
copy build\PicForge.jar C:\Users\%USERNAME%\OneDrive\Desktop
copy build\PicForge.jar C:\Users\%USERNAME%\Desktop

@rem run PicForge
java -jar build\PicForge.jar

@echo off
if %errorlevel% neq 0 (
    echo Compilation failed!
) else (
    echo Compilation finished successfully!
)
pause