package leagueMapReduce.mapReduce;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Hw3 {

    public static String gameMode;

    public static void main(String[] args) throws Exception {
        gameMode = args[0];
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "WC");
        job.setJarByClass(Hw3.class);
        if (gameMode.compareTo("avg") == 0) {
            job.setMapperClass(AMapper.class);
            job.setReducerClass(AReducer.class);
            job.setOutputValueClass(DoubleWritable.class);
        } else if (gameMode.compareTo("stat") == 0) {
            job.setPartitionerClass(PartitionerS.class);
            job.setNumReduceTasks(3);
            job.setMapperClass(GPMapper.class);
            job.setReducerClass(GPReducer.class);
            job.setOutputValueClass(IntWritable.class);
        } else {
            job.setMapperClass(GPMapper.class);
            job.setReducerClass(GPReducer.class);
            job.setOutputValueClass(IntWritable.class);
        }
        job.setOutputKeyClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}