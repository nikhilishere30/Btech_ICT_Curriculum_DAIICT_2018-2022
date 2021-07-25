package mrprograms;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class mrprogramsreducer extends Reducer<Text, Text, Text,Text>{
	public void reduce(Text key, Iterable<Text> values,
			   Context c)
			   throws IOException, InterruptedException{
		
		int sum=0,count=0,mini=Integer.MAX_VALUE,maxi=Integer.MIN_VALUE,avg;
		for(Text i:values)
		{
			count++;
			sum+=Integer.parseInt(i.toString());
			mini=Math.min(mini,Integer.parseInt(i.toString()));
			maxi=Math.max(maxi,Integer.parseInt(i.toString()));
		}
		avg=sum/count;
		String s1,s2,s3;
		s1=Integer.toString(mini);
		s2=Integer.toString(avg);
		s3=Integer.toString(maxi);
		s1=s1+" "+s2+" "+s3;
		c.write(key,new Text(s1));
	}

}
