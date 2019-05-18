package leagueMapReduce.mapReduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class GPMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    public static String gameMode = Hw3.gameMode;
    private Text word = new Text();
    private IntWritable one = new IntWritable();

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Splitting the line on spaces
        if (gameMode.compareTo("game") == 0) {
            String[] stringArr = value.toString().split("\\s+");
            for (String str : stringArr) {
                if (!isStringInt(str)) {
                    word.set(str);
                    one.set(1);
                    context.write(word, one);
                }
            }

        }
        if (gameMode.compareTo("point") == 0) {
            String[] stringArr = value.toString().split("\\n+");
            for (String str : stringArr) {
                String match[] = str.split(" ");
                int len = match.length;
                for (int i = 0; i < len / 3; i++) {
                    String result = new String();
                    if (Integer.parseInt(match[i * 3 + 2]) == 1) {
                        result = match[i * 3 + 0];
                        one.set(3);
                    } else if (Integer.parseInt(match[i * 3 + 2]) == 0) {
                        result = match[i * 3 + 0];
                        one.set(1);
                        word.set(result);
                        context.write(word, one);
                        result = match[i * 3 + 1];
                    } else if (Integer.parseInt(match[i * 3 + 2]) == 2) {
                        result = match[i * 3 + 1];
                        one.set(3);
                    }
                    word.set(result);
                    context.write(word, one);
                }
            }
        }
        if (gameMode.compareTo("stat") == 0) {
            String[] stringArr = value.toString().split("\\n+");
            for (String str : stringArr) {
                String match[] = str.split(" ");
                int len = match.length;
                for (int i = 0; i < len / 3; i++) {
                    String result = new String();
                    if (Integer.parseInt(match[i * 3 + 2]) == 1) {
                        result = "HOME_WIN";
                        one.set(1);
                    } else if (Integer.parseInt(match[i * 3 + 2]) == 0) {
                        result = "DRAW";
                        one.set(1);
                    } else if (Integer.parseInt(match[i * 3 + 2]) == 2) {
                        result = "AWAY_WIN";
                        one.set(1);
                    }
                    word.set(result);
                    context.write(word, one);
                }
            }
        }
    }
}