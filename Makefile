ready:
	source ~/.bashrc
game:
	hadoop com.sun.tools.javac.Main *.java && jar cf MapReduce.jar *.class && rm -rf output_g/ && hadoop jar MapReduce.jar Hw3 game input output_g && bin/hadoop fs -cat output_g/part-r-00000
point:
	hadoop com.sun.tools.javac.Main *.java && jar cf MapReduce.jar *.class && rm -rf output_p/ && hadoop jar MapReduce.jar Hw3 point input output_p && bin/hadoop fs -cat output_p/part-r-00000
avg:
	hadoop com.sun.tools.javac.Main *.java && jar cf MapReduce.jar *.class && rm -rf output_a/ && hadoop jar MapReduce.jar Hw3 avg input output_a && bin/hadoop fs -cat output_a/part-r-00000
stat:
	hadoop com.sun.tools.javac.Main *.java && jar cf MapReduce.jar *.class && rm -rf output_s/ && hadoop jar MapReduce.jar Hw3 stat input output_s && bin/hadoop fs -cat output_s/part-r-00000 && bin/hadoop fs -cat output_s/part-r-00001 && bin/hadoop fs -cat output_s/part-r-00002
