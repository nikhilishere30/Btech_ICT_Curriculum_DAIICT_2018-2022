package pmj.spark.demo.dataframe;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.max;

public class EmployeeDF1 {

	public static void main(String[] args) {

	    SparkSession spark = SparkSession.builder()
	            .appName("Spark Demo")
	            .master("local")
	            .getOrCreate();
		
	    StructType schema = DataTypes.createStructType(new StructField[] {
	    		//eno,name,dob,gender,salary,sup_eno,dno	    		
			DataTypes.createStructField("eno", DataTypes.StringType, false),
	        DataTypes.createStructField("name", DataTypes.StringType, true),
	        DataTypes.createStructField("dob", DataTypes.DateType, true),
	        DataTypes.createStructField("gender", DataTypes.StringType, true),
	        DataTypes.createStructField("salary", DataTypes.IntegerType, true),
	        DataTypes.createStructField("sup_eno", DataTypes.StringType, true),
	        DataTypes.createStructField("dno", DataTypes.IntegerType, true)
	    });
	    //System.out.println(schema.prettyJson());
	    
	    Dataset<Row> emp = spark.read()
	            .option("header", "true")
	            .option("sep", ",")
	    		.schema(schema)
	            .csv("data/employee_m.csv");
	    
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
	    emp.orderBy( emp.col("dno"), emp.col("salary")).show();

	    //Filter/SELECTION
	    System.out.println("dno =4 and salary > 30000:");
	    emp.filter( "salary > 30000").show();
	    emp.where( "salary > 40000").show();
	    System.out.println("dno=5 and salary > 40000:");
	    emp.filter( emp.col("salary").geq(40000).and(emp.col("dno").equalTo(5))).show();	    
	*/
	    //Project
	    System.out.println("dno=4:");
	    emp.select("eno", "name", "salary").where("dno == 4").show();
	    emp.select(emp.col("eno"), emp.col("name"), emp.col("salary").multiply(1.1))
	    .where("dno == 4").show();
	    
	    //people.filter("age".gt(30))
	    // .join(department, people.col("deptId").equalTo(department("id")))
	    // .groupBy(department.col("name"), "gender")
	    // .agg(avg(people.col("salary")), max(people.col("age")));
	 	    
	    System.out.println("Aggregate: avg, and max salary");
	    emp.agg(avg(emp.col("salary")), max(emp.col("salary"))).show();
	    
	    System.out.println("Aggregate: deptwise avg, and max salary");
	    emp.groupBy(emp.col("dno"))
	    .agg(avg(emp.col("salary")), max(emp.col("salary"))).show();
	    
	}

}


