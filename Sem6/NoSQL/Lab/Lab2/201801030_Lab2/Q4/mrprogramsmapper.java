package mrprograms;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class mrprogramsmapper extends Mapper<LongWritable,Text,Text,Text> {

	public void map(LongWritable key,Text value,Context c) throws IOException,InterruptedException
	{
		
		String request=value.toString();
		String time=request.split(" ")[3];
		String error_code=request.split(" ")[8];
		String url =request.split(" ")[6];  
		
		//String ans=time+", "+url;
		
		if(error_code.equals("404")) 
		c.write(new Text(time),new Text(url));
		
	}
}
