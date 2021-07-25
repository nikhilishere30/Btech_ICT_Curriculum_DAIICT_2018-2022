package Lab08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.ValidationOptions;

public class PostsMain {
	
	public static void main(String[] args) {
		//connect to mongoDB atlas
		 MongoClient mongoClient = MongoClients.create( "<your-mongodb-atlas-string>" );
		 
		 //Getting the requires database
		 
		 MongoDatabase database = mongoClient.getDatabase("mongodb"); //you can change the name of the database to whatever you want
		 
		 //validation options for the collection "posts"
		 ValidationOptions collOptions = new ValidationOptions().validator(
			        Filters.or(Filters.exists("title"), Filters.exists("body"),Filters.exists("tags")));
//		 Creation of collection posts
			database.createCollection("posts",
			        new CreateCollectionOptions().validationOptions(collOptions));
//			Getting the collection 'posts"
			MongoCollection<Document> collection = database.getCollection("posts");
			
			//Inserting a new documents 
			Document post1 = new Document("title", "NoSQL Setup").append("body", "This is the setup lab for mongodb")
					.append("tags", Arrays.asList("mongodb","database"));
			
			collection.insertOne(post1);
			
			
			//Inserting many documents
			Document post2 = new Document("title", "Inserting documents").append("body", "Insertion of documents can be done on a mongo shell using insert command")
					.append("tags", Arrays.asList("mongodb","insert"));
			
			
			Document post3 = new Document("title", "Show collections").append("body", "By writing show collections in mongo shell you can see the collections that the database have.")
					.append("tags", Arrays.asList("mongodb","collection"));

			
			Document post4 = new Document("title", "Object Id").append("body", "ObjectId is a special type of datatype used by mongoDB to identify each document.")
					.append("tags", Arrays.asList("mongodb","objectID"));
			
			List<Document> documents = new ArrayList<Document>();
			documents.add(post2);
			documents.add(post3);
			documents.add(post4);
			
			collection.insertMany(documents);
			
			// Query all the elements of a collection
			System.out.println("\nQuerying all the documents in collection posts");
			Consumer<Document> printConsumer = new Consumer<Document>() {
			       @Override
			       public void accept(final Document document) {
			           System.out.println(document.toJson());
			       }
			};
			
			collection.find().forEach(printConsumer);
			
			//Query using filter criteria
			System.out.println("\nQuery using filter criteria");
			collection.find(Filters.eq("title","Inserting documents")).forEach(printConsumer);
			
			//Update a document
			System.out.println("\nChecking the updated document");
			collection.updateOne(
					Filters.eq("title","Inserting documents"),
	                Updates.combine(Updates.set("title", "Update document"), Updates.set("body", "Documents can be updated on a mongo shell using update command ")));
			collection.find(Filters.eq("title","Update document")).forEach(printConsumer);
			
			//Delete a document
			System.out.println("\nChecking the collection after deletion");
			collection.deleteOne(Filters.eq("title", "Update document"));
			collection.find().forEach(printConsumer);

			
	}

}