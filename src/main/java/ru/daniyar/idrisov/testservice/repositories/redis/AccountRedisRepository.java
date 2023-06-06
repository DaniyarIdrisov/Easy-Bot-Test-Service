package ru.daniyar.idrisov.testservice.repositories.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import ru.daniyar.idrisov.testservice.models.redis.AccountRedis;

@Repository
public interface AccountRedisRepository extends KeyValueRepository<AccountRedis, String> {
}
