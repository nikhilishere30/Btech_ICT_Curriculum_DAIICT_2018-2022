package mrprograms;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class mrprogramsmapper extends Mapper<LongWritable,Text,Text,Text>{
	
	public void map(LongWritable key, Text value, Context c)
			   throws IOException, InterruptedException{
		String line=value.toString();
		String[] arr =line.split(",",10);
		//String ans=arr[1]+" "+arr[3];
		
		if(Integer.parseInt(arr[4])>0)
		{
			c.write(new Text(arr[6]), new Text(arr[4]));
		}
			
	}

}
