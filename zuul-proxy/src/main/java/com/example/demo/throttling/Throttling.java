package com.example.demo.throttling;

import com.example.demo.data.User;
import com.netflix.zuul.context.RequestContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.apache.commons.codec.binary.Base64;

@Service
public class Throttling {
    @Value("${api.max.per.hour}")
    private int maxCall;

    @Autowired
    private MongoRepository<User,String> repo;

    public Throttling(MongoRepository<User, String> repo) {
        this.repo = repo;
    }

    public boolean allowed(RequestContext context) {
        boolean allowed;
        String encoded = context.getRequest().getHeader("api-token");

        byte[] decoded = Base64.decodeBase64(encoded);
        String str = new String(decoded);
        JSONObject json = new JSONObject(str);
        maxCall = json.getInt("max-call");
        String token = json.getString("token");

        if (token == null) return false;
        System.err.println("Throttling token " + token);
        if(repo.existsById(token)){
            User tmp = repo.findById(token).get();
            LocalDateTime now = LocalDateTime.now();
            int dur = (int) Duration.between(now,tmp.getFirstCall()).toMinutes();
            allowed = (dur < 60) && (tmp.getnCall() < maxCall);
            System.err.println("AUTORIZZATO: "+allowed);

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
            tmp.setMaxCall(maxCall);
            repo.save(tmp);
            return true;
        }
    }
}
