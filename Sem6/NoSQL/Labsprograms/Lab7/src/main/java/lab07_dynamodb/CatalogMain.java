package lab07_dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import java.util.HashSet;
import java.util.Arrays;

//Code Source: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html

public class CatalogMain {
	
	public static void main(String[] args) {

		//AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
		
		//Task 1: Add(Put) an Item.
        CatalogItem item = new CatalogItem();
        item.setId(601);
        item.setTitle("Book 601");
        item.setISBN("611-1111111111");
        item.setBookAuthors(new HashSet<String>(Arrays.asList("Author1", "Author2")));
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(item);
        
		//Task 2: Retrieve(Get) the item.
        CatalogItem itemRetrieved = mapper.load(CatalogItem.class, 601);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);

        //Task 3: Update the item.
        itemRetrieved.setISBN("622-2222222222");
        itemRetrieved.setBookAuthors(new HashSet<String>(Arrays.asList("Author1", "Author3")));
        mapper.save(itemRetrieved);
        System.out.println("Item updated:");
        System.out.println(itemRetrieved);        
		
        //Task 4: Retrieve the updated item.
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
            .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
        .build();
        CatalogItem updatedItem = mapper.load(CatalogItem.class, 601, config);
        System.out.println("Retrieved the previously updated item:");
        System.out.println(updatedItem);

        //Task 5: Delete the item.
        mapper.delete(updatedItem);

        //Task 6: Try to retrieve deleted item.
        CatalogItem deletedItem = mapper.load(CatalogItem.class, updatedItem.getId(), config);
        if (deletedItem == null) {
            System.out.println("Done - Sample item is deleted.");
        }        
	}
}