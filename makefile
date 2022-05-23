##
# JMinesweeper
#
# @file
# @version 0.1
VPATH = JMinesweeper

jar: Minesweeper.class Main.class Main$$Handler.class
	jar cfm JMinesweeper.jar jar-manifest.txt ./JMinesweeper/*.class

Minesweeper.class: Minesweeper.java
	javac -d ./ ./JMinesweeper/Minesweeper.java

Main.class: Main.java
	javac -d ./ ./JMinesweeper/Main.java

Main$$Handler.class: Main.java
	javac -d ./ ./JMinesweeper/Main.java

clean:
	rm -f ./JMinesweeper.jar
	rm -f ./JMinesweeper/*.class

# end
