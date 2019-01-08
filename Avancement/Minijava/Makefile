LIB = ./lib
SRC = ./src
BIN = ./bin
NAME = spec/minijava
SRCJFLEX = ${NAME}.jflex
SRCCUP   = ${NAME}.cup
CUPOPT=-locations
PKG = syntax
MAIN = main/Compil
ARGS=input.txt
JARFILE=Run.jar
DST = ${SRC}/${PKG}

JAVA = java
JAVAC = javac -g
JFLEX = $(JAVA) -jar $(LIB)/jflex.jar -d ${DST} -nobak 
CUP   = $(JAVA) -jar $(LIB)/java-cup.jar -destdir ${DST} ${CUPOPT}
LIBRUNTIME=$(LIB)/java-cup-runtime.jar
CP =  $(BIN):$(LIBRUNTIME) 
GEN = ${DST}/Yylex.java  ${DST}/parser.java  ${DST}/sym.java ${JARFILE}

default: compile

usage : 
	@echo -e "USAGE : \n\
 make , make run \n\
 make flex, make cup, make generate, make compile. \n\
 make runjar, make jar \n\
 make mars, make marsIDE \n\
 make clean"

jflex ${DST}/Yylex.java : ${SRCJFLEX}
	${JFLEX} ${SRCJFLEX}

cup ${DST}/parser.java : ${SRCCUP}
	${CUP} ${SRCCUP}

generate :  ${DST}/Yylex.java  ${DST}/parser.java 

compile :  generate
	@mkdir -p $(BIN)
	$(JAVAC) -cp $(CP) -d $(BIN) -sourcepath ${SRC}  ${SRC}/${MAIN}.java

run :  compile
	$(JAVA) -cp $(CP)  ${MAIN} ${ARGS}

jar ${JARFILE} : compile
	-/bin/rm -f ${JARFILE}
	-/bin/cp ${LIBRUNTIME} ${JARFILE}
	jar ufe ${JARFILE} ${MAIN} -C ${BIN} .

runjar : ${JARFILE}
	${JAVA} -jar ${JARFILE} ${ARGS}

mars: input.mips
	${JAVA} -jar ${LIB}/mars.jar input.mips

marsIDE: 
	${JAVA} -jar ${LIB}/mars.jar

clean:
	-/bin/rm -fr $(BIN)
	-rm -f ${GEN}
	-rm -f *~ */*~ *.class Yylex.java 
	rm -rf input.mips Exemples/*.mips Exemples/*/*.mips

