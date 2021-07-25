package q2;



import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.max;

public class q2 {

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
	        DataTypes.createStructField("zip", DataTypes.StringType, true),
	        DataTypes.createStructField("year", DataTypes.IntegerType, true),
	        DataTypes.createStructField("price", DataTypes.IntegerType, true)
	    });
	    //System.out.println(schema.prettyJson());
	    
	    Dataset<Row> house = spark.read()
	            .option("header", "true")
	            .option("sep", ",")
	    		.schema(schema)
	            .csv("data/houses.csv");
	    
		        //.option("dateFormat", "dd-mm-yy")
		        //.schema(schema)
        		//.option("inferSchema", "true")

	 /*
	    System.out.println("Employee Schema:");
	    emp.printSchema();

	    //show all rows
	    System.out.println("Employees:");
	    emp.show();

	    //show first 3 rows
	    System.out.println("First 3:");
	    emp.show(3);

	    //Order By
	    System.out.println("Sort:");
	    emp.sort( emp.col("dno"), emp.col("salary").desc()).show();
	        
	    System.out.println("Order By:");
	    emp.orderBy( emp.col("dno"), emp.col("salary")).show();*/

	    //Filter/SELECTION
//	    System.out.println("beds>=3 and baths>=2:");
//	    house.filter( "baths > 2").show();
//	    house.where( "beds > 3").show();
//	    System.out.println("dno=5 and salary > 40000:");
//	    house.filter("area>2000");
	    //house.filter(house.col("area").gt(2000).and(house.col("price").leq(400000))).show();//.and(house.col("price").leq(40000)));
	    //house.select("ID","year","zip","price","area").show();
	     house.select("ID","year","zip").where(house.col("area").gt(2000).and(house.col("price").leq(400000))).show();
//	
//	    //Project
//	    System.out.println("dno=4:");
//	    emp.select("eno", "name", "salary").where("dno == 4").show();
//	    emp.select(emp.col("eno"), emp.col("name"), emp.col("salary").multiply(1.1))
//	    .where("dno == 4").show();
//	    
//	    //people.filter("age".gt(30))
//	    // .join(department, people.col("deptId").equalTo(department("id")))
//	    // .groupBy(department.col("name"), "gender")
//	    // .agg(avg(people.col("salary")), max(people.col("age")));
//	 	    
//	    System.out.println("Aggregate: avg, and max salary");
//	    emp.agg(avg(emp.col("salary")), max(emp.col("salary"))).show();
//	    
//	    System.out.println("Aggregate: deptwise avg, and max salary");
//	    emp.groupBy(emp.col("dno"))
//	    .agg(avg(emp.col("salary")), max(emp.col("salary"))).show();
	    
	}

}
