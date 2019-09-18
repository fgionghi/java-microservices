package com.example.demo.throttling;

import com.example.demo.data.User;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class Throttling {
    @Value("${api.max.per.hour}")
    private int maxCall;

    @Autowired
    private MongoRepository<User,String> repo;

    private boolean allowed;

    public boolean allowed(RequestContext context) {
        String token = context.getRequest().getHeader("api-token");
        if (token == null) return false;
        System.err.println("Throttling token " + token);
        System.err.println("Repo: " + repo);
        if(repo.existsById(token)){
            User tmp = repo.findById(token).get();
            LocalDateTime now = LocalDateTime.now();
            int dur = (int) Duration.between(now,tmp.getFirstCall()).toMinutes();
            allowed = (dur < 60) && (tmp.getnCall() < maxCall);
            if(allowed)
            {
                tmp.setnCall(tmp.getnCall()+1);
                repo.save(tmp);
                return true;
            } else {
                return false;
            }
        }
        else {
            User tmp = new User();
            tmp.setToken(token);
            tmp.setFirstCall(LocalDateTime.now());
            tmp.setnCall(1);
            repo.save(tmp);
            return true;
        }
    }
}
