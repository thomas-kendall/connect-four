package connect.four.web.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "connect.four.web.repository")
public class DynamoDBConfig {

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		// Uses environment variables: AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		return amazonDynamoDB;
	}
}
