package com.travel.web.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.travel.web.entity.Notice;
import com.travel.web.entity.NoticeView;
import com.travel.web.service.NoticeService;

@WebServlet("/notice/Notice")
public class NoticeListController extends HttpServlet{
	// 404 : �ƿ� �����Ҽ� �ִ� url�� ����
	// 405 : ������ �ִ� �޼ҵ尡 ����
	// 403 : url���ְ� �޼ҵ嵵 ������ ������ ����(���ȿ���)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list?f=title&q=a ���·� �˻��� �ϸ� ���޵�, �˻��� �ʼ��� �ƴ϶� �ɼ�
		
		String field_ = request.getParameter("f"); // list.jsp �˻�â���� �Ѱ��ִ� ��
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		String field = "title"; // �⺻��(����) ó��
		if(field_ != null && !field_.equals(""))
			field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals(""))
			query = query_;
		
		int page = 1;
		if(page_ != null && !page_.equals(""))
			page = Integer.parseInt(page_);
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page); // �˻�
		
		int count = service.getNoticeCount(field, query);
		
		
		request.setAttribute("list", list);
		request.setAttribute("count", count); // �信�� ����
		
		// �������� �������� : redirect : �� ���޸� ��?, forward : ���� �۾��ߴ� ������ �ٸ������� �̾����'
		request.getRequestDispatcher("/WEB-INF/view/notice/Notice.jsp").forward(request, response);
		// notice�ȿ� �ִ� detail.jsp�� ��û�ϸ鼭 ���� ����ϴ� �����(request)�� ��µ���(response)�� ����


	
	}

}
