package leagueMapReduce.mapReduce;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private Text word = new Text();
    private DoubleWritable one = new DoubleWritable();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] stringArr = value.toString().split("\\n+");
        for (String str : stringArr) {
            String match[] = str.split(" ");
            int len = match.length;
            for (int i = 0; i < len / 3; i++) {
                String result = new String();
                if (Integer.parseInt(match[i * 3 + 2]) == 1) {
                    result = match[i * 3 + 0];
                    one.set(3);
                    word.set(result);
                    context.write(word, one);
                    result = match[i * 3 + 1];
                    one.set(0);
                    word.set(result);
                    context.write(word, one);
                } else if (Integer.parseInt(match[i * 3 + 2]) == 0) {
                    result = match[i * 3 + 0];
                    one.set(1);
                    word.set(result);
                    context.write(word, one);
                    result = match[i * 3 + 1];
                    word.set(result);
                    context.write(word, one);
                } else if (Integer.parseInt(match[i * 3 + 2]) == 2) {
                    result = match[i * 3 + 1];
                    one.set(3);
                    word.set(result);
                    context.write(word, one);
                    result = match[i * 3 + 0];
                    one.set(0);
                    word.set(result);
                    context.write(word, one);
                }

            }
        }
    }
}