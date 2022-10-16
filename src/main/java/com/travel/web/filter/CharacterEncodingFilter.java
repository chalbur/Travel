package com.travel.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

// 일일이 서블릿에 필터코드를 넣기 귀찮기 때문에 필터패키지를 따로 만듬
@WebFilter("/*") // 필터 맵핑 작업 , 어노테이션을 이용함.
public class CharacterEncodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		
		chain.doFilter(request, response);
		

	}

}
