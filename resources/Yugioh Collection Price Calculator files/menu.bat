@echo off
mode con:cols=200 lines=40

echo Welcome to Yugioh Collection Price Calculator!

set /p choice=[Do you want to use the Normal version or Efficient version? n/e]

echo %choice%

IF %choice%==e (
	echo The efficient option have been choose, the program will start.
	java -jar YugiohPriceCalc_efficient.jar
) ELSE IF %choice%==n (
	echo The normal option have been choose, the program will start.
	java -jar YugiohPriceCalc_normal.jar
) ELSE (
	echo invalid option. the program will start in the normal version.
	java -jar YugiohPriceCalc_normal.jar
)


set /p variable=[Press any key to exit...]