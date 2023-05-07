package top.ashun.recruit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 18483
 */
@Service
public class RedisService {
    @Autowired
    public RedisTemplate redisTemplate;

    public <T> T getStringValue(Object key, Class<T> beanClass) throws JsonProcessingException, IllegalAccessException {
        if (key == null) {
            return null;
        }
        Object o = redisTemplate.opsForValue().get(key);
        ObjectMapper objectMapper = new ObjectMapper();
        return o == null ? null : objectMapper.readValue(objectMapper.writeValueAsString(o), beanClass);
    }
}