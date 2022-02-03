cls

java -cp ./test;./lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.disasm test/%1.obj

java -cp ./test;./lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run -debug test/%1.obj

pause
