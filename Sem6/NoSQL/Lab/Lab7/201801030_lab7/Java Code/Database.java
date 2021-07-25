package lab07;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;



public class Database {

	public static AmazonDynamoDB client=AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-south-1"))
            .build();
	
	public static DynamoDBMapper mapper = new DynamoDBMapper(client);
	
	
	
	public static void Question_1(int user_id,String user_name,String password,String email)
	{
		
		Users users=new Users();
		
        users.setuser_id(user_id);
        users.setuser_name(user_name);
        
        users.setpassword(password);
        users.setemail(email);

        mapper.save(users);
        
        Users Item_Retrieved=mapper.load(Users.class, user_id);
        System.out.println(Item_Retrieved); 
	}
	
	public static void Question_2(int qid,int user_id,String question_text,String timestamp,String tags)
	{
		
		Questions questions=new Questions();
		
        questions.setqid(qid);
        questions.setuser_id(user_id);
        
        questions.setquestion_text(question_text);
        questions.settimestamp(timestamp);
        questions.settags(tags);

        mapper.save(questions);
        
        Questions Item_Retrieved=mapper.load(Questions.class, qid);
        System.out.println(Item_Retrieved);
	}
	
	
	public static void Question_3(int qid,int answer_seq_no,int user_id,String answer_text,String timestamp,int thumbs_up,int thumbs_down)
	{
		Answers answers=new Answers();
		
		answers.setqid(qid);
		answers.setanswer_seq_no(answer_seq_no);
		
		
		answers.setthumbs_down(thumbs_down);
        
		answers.setuser_id(user_id);
		answers.setanswer_text(answer_text);
		answers.setthumbs_up(thumbs_up);
        
		answers.settimestamp(timestamp);
		

        mapper.save(answers);
        
        Answers Item_Retrieved=mapper.load(Answers.class,qid,answer_seq_no);
        
        System.out.println(Item_Retrieved);
        
	}
	
	public static void Question_4(int qid,int answer_seq_no,int thumbs_up,int thumbs_down) throws Exception 
	{
		Answers answers=new Answers();
		
		DynamoDBMapper mapper=new DynamoDBMapper(client);
		
	    Answers Item_Retrieved=mapper.load(Answers.class,qid,answer_seq_no);

	    Item_Retrieved.setthumbs_up(thumbs_up);
	    Item_Retrieved.setthumbs_down(thumbs_down);
        mapper.save(Item_Retrieved);

        DynamoDBMapperConfig config=DynamoDBMapperConfig.builder()
            .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
            .build();
        
        Answers updated=mapper.load(Answers.class, qid,answer_seq_no);
 
        System.out.println(updated);
	}
	
	public static void Question_5(Integer user_id)
	{
		
		String temp=user_id.toString();
		
		Map<String, AttributeValue>pair=new HashMap<String,AttributeValue>();
        pair.put(":val",new AttributeValue().withN(temp));
        
        DynamoDBScanExpression scanExpression=new DynamoDBScanExpression()
                .withFilterExpression("user_id=:val").withExpressionAttributeValues(pair);

        PaginatedScanList<Questions>scannedResult=mapper.scan(Questions.class,scanExpression);

        for(Questions ques:scannedResult)
        System.out.println(ques);
        
	}
	
	public static void Question_6(Integer qid)
	{
		String temp=qid.toString();
		
		Map<String, AttributeValue>pair=new HashMap<String, AttributeValue>();
        pair.put(":val",new AttributeValue().withN(temp));
        
        
        DynamoDBScanExpression scannedExpression=new DynamoDBScanExpression()
                .withFilterExpression("qid=:val").withExpressionAttributeValues(pair);

        PaginatedScanList<Answers>scannedResult=mapper.scan(Answers.class,scannedExpression);
        

        for(Answers answer:scannedResult)
        System.out.println(answer);
        
	}
	
	public static void Question_7(Integer user_id)
	{
		
		String temp=user_id.toString();
		
		Map<String, AttributeValue>pair=new HashMap<String,AttributeValue>();
        pair.put(":val",new AttributeValue().withN(temp));
        
        
        DynamoDBScanExpression scannedExpression=new DynamoDBScanExpression()
                .withFilterExpression("user_id=:val").withExpressionAttributeValues(pair);
        

        PaginatedScanList<Answers> scannedResult=mapper.scan(Answers.class,scannedExpression);

        for(Answers answer:scannedResult)
        System.out.println(answer);
        
	}
	
	
	public static void Question_8(String user_name)
	{
		
		Map<String, AttributeValue>pair1=new HashMap<String,AttributeValue>();
        pair1.put(":val",new AttributeValue().withS(user_name));
        
        DynamoDBScanExpression scannedExpression1=new DynamoDBScanExpression()
                .withFilterExpression("user_name=:val").withExpressionAttributeValues(pair1);

        PaginatedScanList<Users>scannedResult1=mapper.scan(Users.class,scannedExpression1);

        for (Users user:scannedResult1) 
        {
        	
        	String temp=(user.getuser_id()).toString();
   
        	Map<String, AttributeValue>pair2=new HashMap<String,AttributeValue>();
            pair2.put(":val1",new AttributeValue().withN(temp));
            pair2.put(":val2",new AttributeValue().withN("0"));
            
            
            DynamoDBScanExpression scannedExpression2=new DynamoDBScanExpression()
                    .withFilterExpression("user_id=:val1 and thumbs_up>:val2").withExpressionAttributeValues(pair2);
            
            PaginatedScanList<Answers>scannedResult2=mapper.scan(Answers.class,scannedExpression2);
            
            for(Answers answer:scannedResult2) 
            System.out.println(answer);
           
        }
	}
	
	
	
    public static void main(String[] args) throws Exception 
    {
    	System.out.println("Add three users");
    	
    	Question_1(101,"User1","u1","user1@gmail.com");
    	Question_1(102,"User2","u2","user2@gmail.com");
    	Question_1(103,"User3","u3","user3@gmail.com");

    	System.out.println(" ");
    	
    	long offset1=Timestamp.valueOf("2020-01-01 00:00:00").getTime();
    	long end1=Timestamp.valueOf("2020-02-01 00:00:00").getTime();
    	long diff1=end1 - offset1 + 1;
    	
    	
    	System.out.println("Add five questions");
    	
    	Timestamp q1=new Timestamp(offset1+(long)(Math.random()*diff1));
    	Question_2(1,101,"Question1",q1.toString(),"tag1");
    	
        Timestamp q2=new Timestamp(offset1+(long)(Math.random()*diff1));
        Question_2(2,102,"Question2",q2.toString(),"tag2");
        
        Timestamp q3=new Timestamp(offset1+(long)(Math.random()*diff1));
        Question_2(3,103,"Question3",q3.toString(),"tag3");
        
        
        Timestamp q4=new Timestamp(offset1+(long)(Math.random()*diff1));
        Question_2(4,101,"Question4",q4.toString(),"tag4");
        
        
        Timestamp q5=new Timestamp(offset1+(long)(Math.random()*diff1));
        Question_2(5,102,"Question5",q5.toString(),"tag5");
        
        
    	long offset2 = Timestamp.valueOf("2020-02-01 00:00:00").getTime();
    	long end2 = Timestamp.valueOf("2020-03-01 00:00:00").getTime();
    	long diff2 = end2 - offset2 + 1;
    	System.out.println(" ");
    	
    	
    	
    	System.out.println("Add 10 answers to any 4 questions");
    	
        Timestamp a1=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(1,1,101,"Answer1",a1.toString(),0,0);
        
        Timestamp a2=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(2,1,102,"Answer2",a2.toString(),0,0);
        
        Timestamp a3=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(3,1,103,"Answer3",a3.toString(),0,0);
        
        
        Timestamp a4=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(4,1,101,"Answer4",a4.toString(),0,0);
        
        
        Timestamp a5=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(1,2,102,"Answer5",a5.toString(),0,0);
        
        
        Timestamp a6=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(2,2,103,"Answer6",a6.toString(),0,0);
        
        
        Timestamp a7=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(3,2,101,"Answer7",a7.toString(),0,0);
        
        
        Timestamp a8=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(4,2,102,"Answer8",a8.toString(),0,0);
        
        
        Timestamp a9=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(1,3,103,"Answer9",a9.toString(),0,0);
        
        
        Timestamp a10=new Timestamp(offset2+(long)(Math.random()*diff2));
        Question_3(2,3,101,"Answer10",a10.toString(),0,0);
        
        
        System.out.println(" ");
    	System.out.println("Visitor rating an answer");
    	
    	
    	
    	Question_4(4, 1, 10, 10);
    	Question_4(3, 2, 3, 11);
    	Question_4(1, 1, 0, 8);
    	Question_4(2, 3, 4, 3);
    	Question_4(2, 1, 5, 6);
    	Question_4(3, 1, 10, 6);
    	
    	Question_4(1, 2, 2, 7);
    	Question_4(2, 2, 9, 8);
    	
    	Question_4(4, 2, 6, 5);
    	Question_4(1, 3, 0, 1);
    	
    	
    	System.out.println(" ");
    	
    	System.out.println("List all the questions which were posted by the user");
    	Question_5(101);
    	
    	System.out.println(" ");
    	System.out.println("List all answers for a given qid");
    	Question_6(4);
    	
    	System.out.println(" ");
    	System.out.println("List all answers from a given user_id");
    	Question_7(102);
    	
    	System.out.println("");
    	System.out.println("List all answers for a given user_name that has Thumps_up count");
    	Question_8("User3");
    }
}