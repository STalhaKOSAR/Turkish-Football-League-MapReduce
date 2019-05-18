package leagueMapReduce.mapReduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class PartitionerS extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text key, IntWritable value, int numReduceTasks) {
        String stringArr = key.toString();
        if (numReduceTasks == 0) {
            return 0;
        }
        if (stringArr.compareTo("DRAW") == 0) {
            return 0;
        } else if (stringArr.compareTo("HOME_WIN") == 0) {
            return 1;
        } else if (stringArr.compareTo("AWAY_WIN") == 0) {
            return 2;
        } else {
            return 0;
        }
    }
}