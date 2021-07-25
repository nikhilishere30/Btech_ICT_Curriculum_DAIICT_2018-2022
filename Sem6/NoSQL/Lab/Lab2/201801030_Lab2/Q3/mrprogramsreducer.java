package mrprograms;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class mrprogramsreducer extends Reducer<Text,Text,Text,Text> {
	public void reduce(Text key, Iterable<Text> value,Context c) throws IOException,InterruptedException {
		
		Long count=(long)0;
		Long downloadSize=(long)0;
		
		for(Text i:value) {
			count++;
			downloadSize+=Integer.parseInt(i.toString());
		}
		String s1=String.valueOf(count),s2=String.valueOf(downloadSize);
		String ans=s1+ "," +s2;
		c.write(key,new Text(ans));
	}
}
