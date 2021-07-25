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
		String month=request.split(" ")[3].substring(4,12);
		//String type=request.split(" ")[5].substring(1);
		String download_size =request.split(" ")[9];  
		
		if(!download_size.equals("-")) 
		c.write(new Text(month),new Text(download_size));
		
	}
}
