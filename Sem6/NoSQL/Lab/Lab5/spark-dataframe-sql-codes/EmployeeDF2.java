package pmj.spark.demo.dataframe;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.sum;

public class EmployeeDF2 {

	public static void main(String[] args) {

	    SparkSession spark = SparkSession.builder()
	            .appName("Spark Demo").master("local").getOrCreate();
	    
	    Dataset<Row> emp = spark.read()
	            .option("header", "true")
	            .option("sep", ",")
	            .option("inferSchema", "true")
	            .csv("data/employee_m.csv");

	    Dataset<Row> dep = spark.read()
	            .option("header", "true")
	            .option("sep", ",")
	            .option("inferSchema", "true")
	            .csv("data/department_m.csv");

	    Dataset<Row> empdep = emp.join(dep, emp.col("dno").equalTo(dep.col("dno")));
	    empdep.select(emp.col("name"), dep.col("name"), emp.col("salary"))
	    		.show();
	    
	    //empdep.show();
	    //empdep.printSchema();
	    
	    /*
	    empdep.groupBy(dep.col("name"))
    	.agg(sum(emp.col("salary")))
    	.printSchema();
    	*/

	   System.out.println("Print Deptwiese total slary");
	    emp.join(dep, emp.col("dno").equalTo(dep.col("dno")))
	    	.groupBy(dep.col("name"))
	    	.agg(sum(emp.col("salary")))
	    	.show();
	    
	    
	    /*
	    empdep.groupBy(dep.col("name"))
	    	.agg(sum(emp.col("salary")))
	    	.sort(empdep.col("sum(salary)").desc()) //sum(salary)
	    	.show();*/	    
	}

}


