cls

java -cp ./test;./lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run test/%1.obj

pause


rm -rf src/rs/ac/bg/etf/pp1/ast
rm -f src/rs/ac/bg/etf/pp1/sym.java
rm -f src/rs/ac/bg/etf/pp1/MJParser.java
rm -f src/rs/ac/bg/etf/pp1/Yylex.java
rm -f spec/mjparser_astbuild.cup
java -jar lib/JFlex.jar -d src/rs/ac/bg/etf/pp1 spec/mjlexer.flex
echo "Flex completed"
cd src
java -jar ../lib/cup_v10k.jar -destdir rs/ac/bg/etf/pp1 -ast rs.ac.bg.etf.pp1.ast -parser MJParser -buildtree ../spec/mjparser.cup
echo "Cup completed"
cd ../
rm -f src/rs/ac/bg/etf/pp1/Yylex.java~
