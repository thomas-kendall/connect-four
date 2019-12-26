package connect.four.web.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import connect.four.web.entity.GameStateResult;

@EnableScan
public interface GameStateResultRepository extends CrudRepository<GameStateResult, String> {

}
