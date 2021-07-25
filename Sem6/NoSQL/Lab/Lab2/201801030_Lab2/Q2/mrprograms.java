package mrprograms;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;



public class mrprograms {
	public static void main(String[] args) throws Exception,IOException{
		Configuration conf = new Configuration();
		Job job=Job.getInstance(conf,"mr-programs");
		job.setJarByClass(mrprograms.class);
		job.setMapperClass(mrprogramsmapper.class);
		job.setReducerClass(mrprogramsreducer.class);
		//job.setCombinerClass(reduce.class);
		//job.setNumReduceTasks(0);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		Path outpath=new Path(args[1]);
		
		FileInputFormat.addInputPath(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job,outpath);
		outpath.getFileSystem(conf).delete(outpath,true);
		System.exit(job.waitForCompletion(true)?0:1);
	}
}