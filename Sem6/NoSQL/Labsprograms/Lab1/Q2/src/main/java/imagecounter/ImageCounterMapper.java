package imagecounter;

import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class ImageCounterMapper extends Mapper<LongWritable,Text,Text,IntWritable> {

	private final HashSet<String> isImg= new HashSet<String>(new ArrayList<String>(Arrays.asList("jpeg","jpg","png","jif","jfif","jfi","gif","webp","tiff","tif","psd","raw","arw","cr2","nrw","k25","bmp","dib","heif","heic","ind","indd","indt","jp2","j2k","jpx","jpm","mj2")));
	private final HashSet<String> isJPG= new HashSet<String>(new ArrayList<String>(Arrays.asList("jpeg","jpg","jif","jfif","jfi")));
	
	public void map(LongWritable key,Text val,Context c) throws IOException,InterruptedException
	{
		
		String request=val.toString();
		String url= request.split(" ")[6];
		String method= request.split(" ")[5];
		System.out.println(method);
		int i = url.lastIndexOf(".");
		
		if(i !=-1 ) {
			String extension=url.substring(i+1);
			if(isImg.contains(extension) && method.equals("\"GET")) {
				if(extension.equals("gif"))
					c.write(new Text("GIF Images"),new IntWritable(1));
				else if(isJPG.contains(extension))
					c.write(new Text("JPG Images"),new IntWritable(1));
				else
					c.write(new Text("Other Images"),new IntWritable(1));
			}
		}
	}
}

