package q1;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.max;

public class q1_with_sql {

	public static void main(String[] args) {

	    SparkSession spark = SparkSession.builder()
	            .appName("Spark Demo")
	            .master("local")
	            .getOrCreate();
		
	    StructType schema = DataTypes.createStructType(new StructField[] {
	    		//ID,area,beds,baths,zip,year,price	    		
			DataTypes.createStructField("ID", DataTypes.IntegerType, false),
	        DataTypes.createStructField("area", DataTypes.IntegerType, true),
	        DataTypes.createStructField("beds", DataTypes.IntegerType, true),
	        DataTypes.createStructField("baths", DataTypes.IntegerType, true),
	        DataTypes.createStructField("zip", DataTypes.IntegerType, true),
	        DataTypes.createStructField("year", DataTypes.IntegerType, true),
	        DataTypes.createStructField("price", DataTypes.IntegerType, true)
	    });
	   
	    
	    Dataset<Row> house1 = spark.read()
	            .option("header", "true")
	            .option("sep", ",")
	    		.schema(schema)
	            .csv("data/houses.csv");
	    
	    house1.createOrReplaceTempView("house");     
	
	   
	    String q1_sql = "SELECT hou.ID,hou.area,hou.beds,hou.baths,hou.price FROM house hou WHERE beds>=3 AND baths>=2";
	    spark.sql( q1_sql ).show();    
   
	    
	}

}
