package q4;



import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import static org.apache.spark.sql.functions.*;

import scala.collection.immutable.Map;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.max;

import org.apache.spark.sql.Column;

public class q4 {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().
				appName("Spark Demo").master("local").
				getOrCreate();
		
//		StructType schema1 = DataTypes.createStructType(new StructField[] {
//	    		//OrderNo, CustomerID, OrderAmount	    		
//			DataTypes.createStructField("OrderNo", DataTypes.IntegerType, true),
//	        DataTypes.createStructField("CustomerID", DataTypes.IntegerType,true),
//	        DataTypes.createStructField("OrderAmount", DataTypes.IntegerType,true),
//	        
//	    });
//		StructType schema2 = DataTypes.createStructType(new StructField[] {
//	    		//CustomerID, Country, State   		
//			DataTypes.createStructField("CustomerID", DataTypes.IntegerType,true),
//	        DataTypes.createStructField("Country", DataTypes.StringType, true),
//	        DataTypes.createStructField("State", DataTypes.StringType, true),
//	     });
	    //System.out.println(schema.prettyJson());
	    
		Dataset<Row>orders=spark.read().option("header", "true").
				option("sep", ",").option("inferschema","true").
				csv("data/Orders.csv");
		
		
		Dataset<Row>customers=spark.read().option("header", "true").
				option("sep", ",").option("inferschema","true").
				csv("data/Customers.csv");
	    
		        //.option("dateFormat", "dd-mm-yy")
		        //.schema(schema)
        		//.option("inferSchema", "true")

	 
	    
		orders.join(customers,customers.col("CustomerID").
				equalTo(orders.col("CustomerID"))).
		groupBy(customers.col("State")).
		agg(sum(orders.col("OrderAmount"))).show();
//	
//	    
	    
	}
}
