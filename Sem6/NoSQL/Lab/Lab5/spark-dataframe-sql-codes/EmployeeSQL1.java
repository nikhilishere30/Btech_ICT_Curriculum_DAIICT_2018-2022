package pmj.spark.demo.dataframe;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class EmployeeSQL1 {

	public static void main(String[] args) {

	    SparkSession spark = SparkSession.builder()
	            .appName("Spark Demo").master("local").getOrCreate();
		
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

	    //Run SQL statements
	    //register it as a table to run SQL statements on this file

	    emp.createOrReplaceTempView("employee");
	    String sql = "SELECT eno, name, salary FROM employee WHERE dno=4";
	    Dataset<Row> emp_dno4 = spark.sql( sql );
	    emp_dno4.show();
	    emp_dno4.printSchema();
	    		    
	}

}


