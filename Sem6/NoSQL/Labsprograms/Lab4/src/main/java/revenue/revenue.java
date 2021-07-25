package revenue;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class revenue {

	public static void main(String[] args) throws Exception {
		
        SparkConf conf = new SparkConf().setAppName("Total_Revenue").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines1 = sc.textFile("data/Orders.csv");
        JavaRDD<String> lines2 = sc.textFile("data/Customers.csv");
        
        JavaRDD<String[]> records1=lines1.map(line->line.split(","));
        JavaRDD<String[]> records2=lines2.map(line->line.split(","));
        
        //Create Customer ID and Amount pair RDD
	    JavaPairRDD<String,String> CustomerID_State
	    	= records1.mapToPair(rec ->
    			new Tuple2<>(rec[1],rec[2]));
	    
	  //Create Customer ID and State pair RDD
	    JavaPairRDD<String,String> CustomerID_Amount
    	= records2.mapToPair(rec->
			new Tuple2<>(rec[0],rec[2]));
	    
	    
	    //Join the two RDDs
	    JavaPairRDD<String,Tuple2<String,String>> joined=CustomerID_Amount.join(CustomerID_State);
	    
//	    List<Tuple2<String, Tuple2<String, String>>> output3 = joined.collect();
//        output3.forEach( tuple -> System.out.println(tuple));
	    
//	    List<Tuple2<String, String>> output3 = CustomerID_Amount .collect();
//	    output3.forEach( tuple -> System.out.println(tuple));
	    
	    JavaPairRDD<String,String>New_RDD
    	= joined.mapToPair(rec->
			new Tuple2<>(rec._2._1,rec._2._2));
	    
	    List<Tuple2<String, String>> output3 = New_RDD .collect();
    output3.forEach( tuple -> System.out.println(tuple));
	    
	    
	    
	    JavaPairRDD<String, Integer>requiredPair=New_RDD.mapToPair(rec-> new Tuple2<String,Integer>(rec._1, Integer.parseInt(rec._2)));
        JavaPairRDD<String, Integer>answer=requiredPair.reduceByKey((v1,v2)->v1+v2);  
	    
//        List<Tuple2<String, Integer>>output=answer.collect();
//	    output.forEach( tuple -> System.out.println(tuple._1 + " : "+tuple._2));
	   
        
        sc.stop();        
        sc.close();        

	}
}