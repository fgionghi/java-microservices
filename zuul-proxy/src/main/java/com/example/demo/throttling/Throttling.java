package com.example.demo.throttling;

import com.example.demo.data.User;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
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
            maxCall = tmp.getMaxCall();
            LocalDateTime now = LocalDateTime.now();
            int dur = (int) Duration.between(now,tmp.getFirstCall()).toMinutes();
            boolean reset = (dur > 60);
            allowed = ((dur < 60) || reset) && ((tmp.getnCall() < maxCall) || reset);
            System.err.println("AUTORIZZATO: "+allowed);

            if(allowed)
            {
                if(reset){
                    tmp.setFirstCall(now);
                    tmp.setnCall(0);
                }
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
