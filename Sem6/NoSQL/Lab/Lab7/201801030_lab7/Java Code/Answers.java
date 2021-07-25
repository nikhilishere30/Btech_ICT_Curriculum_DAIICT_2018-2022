package lab07;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Answers")

public class Answers {
	
	
	private Integer qid;
	private Integer answer_seq_no;
	private Integer user_id;
	private String answer_text;
	private String timestamp;
	private Integer thumbs_up;
	private Integer thumbs_down;
	
	
	
	@DynamoDBHashKey(attributeName = "qid")
	public Integer getqid() {
		return qid;
	}

    public void setqid(Integer qid) {
        this.qid = qid;
    }
    
    public void settimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    @DynamoDBAttribute(attributeName = "thumbs_up")
    public Integer getthumbs_up() {
		return thumbs_up;
	}

   
   
    @DynamoDBAttribute(attributeName = "user_id")
    public Integer getuser_id() {
		return user_id;
	}

    
    @DynamoDBAttribute(attributeName = "thumbs_down")
    public Integer getthumbs_down() {
		return thumbs_down;
	}

    public void setthumbs_down(Integer thumbs_down) {
        this.thumbs_down = thumbs_down;
    }
    
    public void setthumbs_up(Integer thumbs_up) {
        this.thumbs_up = thumbs_up;
    }
   
    
    public void setuser_id(Integer user_id) {
        this.user_id = user_id;
    }
   
    @DynamoDBAttribute(attributeName = "answer_text")
    public String getanswer_text() {
        return answer_text;
    }
    
    public void setanswer_text(String answer_text) {
        this.answer_text = answer_text;
    }
    
    @DynamoDBAttribute(attributeName = "timestamp")
    public String gettimestamp() {
        return timestamp;
    }
    
    @DynamoDBRangeKey(attributeName = "answer_seq_no")
    public Integer getanswer_seq_no() {
        return answer_seq_no;
    }
    
    public void setanswer_seq_no(Integer answer_seq_no) {
        this.answer_seq_no = answer_seq_no;
    }
    
    
    @Override
    public String toString() {
        return "qid=" + qid + ", answer_seq_no=" + answer_seq_no + ", user_id=" + user_id  +  ", answer_text=" + answer_text + ", timestamp=" + timestamp +  ", thumbs_up=" + thumbs_up + ", thumbs_down=" + thumbs_down;
   }
}