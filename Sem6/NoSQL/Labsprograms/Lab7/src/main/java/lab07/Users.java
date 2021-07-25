package lab07;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Users")

public class Users{
	
	
	private Integer user_id;
	private String email;
	private String user_name;
	private String password;

	 public void setemail(String email) {
	        this.email = email;
	    }
	    

    public void setpassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getemail() {
        return email;
    }

   
    public void setuser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @DynamoDBAttribute(attributeName = "user_name")
    public String getuser_name() {
        return user_name;
    }


    @DynamoDBHashKey(attributeName = "user_id")
	public Integer getuser_id() {
		return user_id;
	}

    
    public void setuser_name(String user_name) {
        this.user_name = user_name;
    }

    @DynamoDBAttribute(attributeName = "password")
    
    public String getpassword() {
        return password;
    }
    
    public String toString(){
        return "user_id=" + user_id + ", user_name=" + user_name + ", password=" + password + ", email=" + email;
    }
    
}