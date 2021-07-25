package q1;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.max;

public class q1 {

	public static void main(String[] args) {

	    SparkSession spark = SparkSession.builder()
	            .appName("Spark Demo")
	            .master("local")
	            .getOrCreate();
		
	    StructType schema = DataTypes.createStructType(new StructField[] {
	    		//ID,area,beds,baths,zip,year,price	    		
			DataTypes.createStructField("ID", DataTypes.StringType, false),
	        DataTypes.createStructField("area", DataTypes.StringType, true),
	        DataTypes.createStructField("beds", DataTypes.IntegerType, true),
	        DataTypes.createStructField("baths", DataTypes.IntegerType, true),
	        DataTypes.createStructField("zip", DataTypes.IntegerType, true),
	        DataTypes.createStructField("year", DataTypes.StringType, true),
	        DataTypes.createStructField("price", DataTypes.IntegerType, true)
	    });
	   
	    
	    Dataset<Row> house = spark.read()
	            .option("header", "true")
	            .option("sep", ",")
	    		.schema(schema)
	            .csv("data/houses.csv");
	    
		        
	
	    //System.out.println("Beds>=3 and baths>=2");
	    house.filter( house.col("beds").geq(3).and(house.col("baths").geq(2))).show();	    
   
	    
	}

}
