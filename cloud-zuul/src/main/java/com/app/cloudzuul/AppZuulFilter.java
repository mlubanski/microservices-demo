package com.app.cloudzuul;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * filter always will let go anything!
 * it is for modifying request or response
 * 
 * you can make here authentication
 * you can make here body encryption
 * you can make here request redirection\
 */
public class AppZuulFilter extends ZuulFilter {

	//defines does filter can modify filter or not (would run() method will be executed)
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();
	    
		return true;
	}

	
	@Override
	public Object run() {
		System.out.println("Zuul filter: passed..");
		return null;
	}

	/*
	 * "pre" for pre-routing filtering (default)
	 * "route" for routing to an origin
	 * "post" for post-routing filters
	 * "error" for error handling
	 * "static" type for static responses (StaticResponseFilter)
	 */
	@Override
	public String filterType() {

		return "pre";
	}

	//defines filter ordering
	@Override
	public int filterOrder() {
		return 0;
	}
	
	/*
	 * By default ZuulFilters are static (they don't carry state)
	 */
	@Override
	public boolean isStaticFilter() {
		return true;
	}

}
