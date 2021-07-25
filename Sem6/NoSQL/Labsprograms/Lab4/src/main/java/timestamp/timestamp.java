package timestamp;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class timestamp {

	public static void main(String[] args) throws Exception {
		
        SparkConf conf = new SparkConf().setAppName("Timestamps").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("data/web_access_log.txt");
        
        JavaRDD<String> values1=lines.filter(rec->Integer.parseInt(rec.split(" ")[8])==404 );
	    
	    
	    JavaPairRDD<String,String> values=values1.mapToPair(rec->new Tuple2<>(rec.split(" ")[3].substring(13,20),rec.split(" ")[6]));
		
	    	                    
	    //collect and output on console
	    List<Tuple2<String,String>> output = values.collect();	    
        output.forEach( tuple -> System.out.println(tuple._1 +" "+ tuple._2));
        
        sc.stop();        
        sc.close();        

	}
}