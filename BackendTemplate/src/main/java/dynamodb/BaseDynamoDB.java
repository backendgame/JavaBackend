package dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class BaseDynamoDB {
    public BasicAWSCredentials awsCreds;
    public AmazonDynamoDB client;
    public DynamoDB dynamoDB;
    protected String accessKey;
    protected String secretKey;

    private BaseDynamoDB() {
//        accessKey= "AKIAZDYALLI35WTSDS3V";
//        secretKey= "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";
        accessKey= "AKIAUOHBVGUQH2JBVHWH";
        secretKey= "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";
        awsCreds = new BasicAWSCredentials(accessKey,secretKey);
        client = AmazonDynamoDBClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.AP_SOUTHEAST_1).build();
        dynamoDB = new DynamoDB(client);
    }
    








    //////////////////////////////////////////
    private static BaseDynamoDB instance;

    public static BaseDynamoDB gI() {
        return instance == null ? instance = new BaseDynamoDB() : instance;
    }
}
