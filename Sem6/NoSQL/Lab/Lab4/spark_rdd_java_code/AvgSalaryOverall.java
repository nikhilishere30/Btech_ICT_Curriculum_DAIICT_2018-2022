package pmj.spark.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class AvgSalaryOverall {

	public static void main(String[] args) throws Exception {
		
        SparkConf conf = new SparkConf().setAppName("firstSparkProject").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("data/employee.csv");
        
	    JavaRDD<String[]> records = lines.map(line -> line.split(","));	    
	    JavaPairRDD<String, Integer> salrecs = records.mapToPair(rec -> new Tuple2<>(rec[6], Integer.parseInt(rec[4])));	    
	    JavaPairRDD<String, Integer> sums = salrecs.reduceByKey((x, y) -> x + y);        
          
        sums.foreach( pair -> System.out.println(pair));
        
        sc.stop();        
        sc.close();

	}
}