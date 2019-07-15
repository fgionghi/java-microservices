package com.example.demo.filters;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
//filtri per proteggere le api analizzando l'header della richiesta
public class SamplepreFilter  extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
		
		return "acquisti".equals(serviceId);
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		if (ctx.getRequest().getHeader("Authorization") == null) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}
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
