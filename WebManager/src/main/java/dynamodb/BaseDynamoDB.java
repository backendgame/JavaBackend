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
        accessKey= "AKIA35D3RS25BX4GYEXW";//DynamoDB_Dev
        secretKey= "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";//DynamoDB_Dev
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
