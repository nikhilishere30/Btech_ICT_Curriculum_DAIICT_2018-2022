package monthly_summary;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class monthly_summary {

	public static void main(String[] args) throws Exception {
		
        SparkConf conf = new SparkConf().setAppName("SalarySummary").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("data/web_access_log.txt");
        
        JavaRDD<String[]> records=lines.map(line->line.split(" "));
        
	    JavaPairRDD<String, Tuple2<Integer,String>> values1 
	    	= records.mapToPair( rec ->
    			new Tuple2<>(rec[3].substring(4,12), 
    					new Tuple2<>(1,rec[9])));
	    
	    JavaPairRDD<String, Iterable<Tuple2<Integer, String>>> 
		groupedRecords = values1.groupByKey();
	    	                    

	    JavaPairRDD<String, String> aggregate = groupedRecords.mapToPair( 
	    		new PairFunction<Tuple2<String, Iterable<Tuple2<Integer, String>>>, String, String>() {
	    			@Override
	    			public Tuple2<String, String> call(
	    					Tuple2<String, Iterable<Tuple2<Integer, String>>> tuple) 
	    							throws Exception {
			    		String key = tuple._1;
			    		Iterable<Tuple2<Integer, String>> values = tuple._2();
			    		int count = 0;
			    		int sum = 0;
			    		for( Tuple2<Integer, String> value : values) {
			    			if(!value._2.equals("-"))
			    			{
			    				count+=value._1;
			    				sum+=Integer.parseInt(value._2);
			    			}
			    		}
			    		String s1=String.valueOf(count),s2=String.valueOf(sum);
			    		s1=s1+","+s2;
			    		return new Tuple2<String, String>(key,s1);
	    			}
	    		});	    
	    
	    //collect and output on console
	    List<Tuple2<String,String>> output = aggregate.collect();	    
        output.forEach( tuple -> System.out.println(tuple._1 + ": " + tuple._2));
        
        sc.stop();        
        sc.close();        

	}
}