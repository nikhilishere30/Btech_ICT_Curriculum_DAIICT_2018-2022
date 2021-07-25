package q3;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.max;

public class q3_with_sql {

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
	    String q3_sql="SELECT hou.ID,hou.zip,hou.price/hou.area AS Price_per_sq_ft FROM house hou ORDER BY Price_per_sq_ft DESC";
	    spark.sql(q3_sql).show();  
   }
}
