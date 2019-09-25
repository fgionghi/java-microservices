package com.example.demo.filters;

import com.example.demo.data.User;
import com.example.demo.throttling.Throttling;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
//filtri per proteggere le api analizzando l'header della richiesta
public class SamplepreFilter  extends ZuulFilter {
	@Autowired
	private MongoRepository<User,String> repo;

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
		
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();

		System.err.println("Throttling start");

		Throttling t = new Throttling(repo);
		if(!t.allowed(ctx)){
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}

		/*if (ctx.getRequest().getHeader("Authorization") == null) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}*/
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}


}
