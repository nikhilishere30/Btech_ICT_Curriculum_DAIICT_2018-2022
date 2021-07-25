package pmj.spark.demo.dataframe;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class EmployeeSQL2 {

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

	    emp.createOrReplaceTempView("employee");
	    dep.createOrReplaceTempView("department");
	    
	    //Run SQL statements
	    //register it as a table to run SQL statements on this file

	    String sql = "SELECT d.name, e.name FROM employee e JOIN "
	    		+ "department d ON (e.dno=d.dno)";
	    spark.sql( sql ).show();

	    sql = "SELECT e.name, s.name FROM employee e LEFT JOIN "
	    		+ "employee s ON (e.sup_eno=s.eno)";
	    spark.sql( sql ).show();

	    sql = "SELECT dno, sum(salary) as TotalSal FROM employee "
	    		+ "group by dno order by sum(salary) desc ";
	    spark.sql( sql ).show();
	    
	    
	}

}


