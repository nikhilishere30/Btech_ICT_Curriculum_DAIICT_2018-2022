package pmj.spark.demo;

import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class SalaryAverageReduceByKey {
	
	//Computes the Average using Reduce By and Mapping
	public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("SalarySummary").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        JavaRDD<String> lines = sc.textFile("data/employee.csv");
	    JavaPairRDD<String, Tuple2<Integer, Integer>> dnoSalPairs 
	    	= lines.mapToPair( line ->
    			new Tuple2<>(line.split(",")[6], 
    					new Tuple2<>(Integer.parseInt(line.split(",")[4]),1))
	    	);                    

	    JavaPairRDD<String, Tuple2<Integer, Integer>> sums
	    	= dnoSalPairs.reduceByKey((v1,v2)
	    		-> new Tuple2<>(v1._1 + v2._1, v1._2 + v2._2));    
	    
	    JavaPairRDD<String, Double> 
	    	aggregate = sums.mapToPair( (v) 
	    	-> new Tuple2<>(v._1, 1.0 * v._2()._1/v._2()._2));

	    //collect and output on console
	    List<Tuple2<String, Double>> output = aggregate.collect();	    
        output.forEach( tuple -> System.out.println(tuple._1 + ": " + tuple._2));
        
        sc.stop();        
        sc.close();        

		//new Tuple2<String, Tuple2<Integer, Integer>>(line.split(",")[6], new Tuple2<Integer, Integer>(Integer.parseInt(line.split(",")[4]),1))

	    /*
	    JavaRDD<String[]> records = lines.map(line -> line.split(","));	    
	    JavaPairRDD<String, Tuple2<Integer, Integer>> dnoSalPairs 
	    	= records.mapToPair(rec -> new Tuple2<>(rec[6], 
	    		new Tuple2<>(Integer.parseInt(rec[4]),1)));
	    		*/            
        
	}	

}

