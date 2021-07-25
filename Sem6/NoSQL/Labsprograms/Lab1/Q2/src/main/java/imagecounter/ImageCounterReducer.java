package imagecounter;

import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class ImageCounterReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
	public void reduce(Text key, Iterable<IntWritable> val,Context c) throws IOException,InterruptedException {
		int sum=0;
		for(IntWritable i:val)
			sum += i.get();
		c.write(key, new IntWritable(sum) );
	}
}