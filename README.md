# TurkishFootballLeague-MapReduce
Create inputs from TFF website and process it in MapReduce

## Installation
First install Apache Hadoop from:
```bash
http://kozyatagi.mirror.guzel.net.tr/apache/hadoop/common/stable/
```
Install guide 
```bash
 https://hadoop.apache.org/docs/r3.2.0/hadoop-project-dist/hadoop- common/SingleCluster.html#Installing_Software 
```
(Note that the most common problem is to forget to set the environment variables on file “hadoop-env.sh“)

You need to add below commands to your ~/.bashrc file:
```bash
export HADOOP_INSTALL="hadoop install path"

export PATH=$PATH:$HADOOP_INSTALL/bin

export PATH=$PATH:$HADOOP_INSTALL/sbin

export HADOOP_MAPRED_HOME=$HADOOP_INSTALL

export HADOOP_COMMON_HOME=$HADOOP_INSTALL

export HADOOP_HDFS_HOME=$HADOOP_INSTALL

export YARN_HOME=$HADOOP_INSTALL

export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_INSTALL/lib/native

export HADOOP_OPTS="-Djava.library.path=$HADOOP_INSTALL/lib"
```

## Usage
For inputCreate, use TFF website's archieve(consisting all match results in a single page)
```bash
http://www.tff.org
```
After you open the archieve page, give URL to inputCreator.
For other specifications read the stdout

When you run the input creator. Inputs.txt file will be created and used in mapReduce

The mapReduce has 4 modes:
```bash
List the number of games played for each team.(game)
```
```bash
List the total points gained by each team. (point)
```
```bash
List the average points per game for each team. (avg)
```
```bash
List the total number of “DRAW”, “HOME_WIN” and “AWAY_WIN” on different files. (stat)
```
Use Makefile to run each of them but make sure that when you open a Terminal run 
```bash 
make ready
```
Example input file created by inputCreator is given under /input directory. Which has match results from 2014 - 2015 Season TFF SuperLeague.
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
