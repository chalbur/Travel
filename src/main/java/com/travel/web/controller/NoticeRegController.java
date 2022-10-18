package com.travel.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.travel.web.entity.Notice;
import com.travel.web.service.NoticeService;

//÷�������� ���� form�� enctype="multipart/form-data"�� ����� ���ڵ������ �ٲ�
//�ذ��ϱ����� �߰��۾�, ��Ƽ��Ʈ�� ���ڵ��� ������ ���������� ��Ƽ��Ʈ�� �޾ƾ��ϹǷ� ������ �������
@MultipartConfig( 
	fileSizeThreshold=1024*1024, // �����ϴ� �����Ͱ� 1mb�� �Ѿ�� ��� ���� ���� ��ũ�� ����
	maxFileSize=1024*1024*50, // �ѹ��� ������ �����;� ����, ���� �ϳ��� ������(50mb)
	maxRequestSize=1024*1024*5*50 // ���� ��ü�� �뷮 (250mb)
)
@WebServlet("/notice/reg")// ��Ʈ���� �ε����� ��û�ϸ� ���⼭ ����
public class NoticeRegController extends HttpServlet{
	// �۾��⸦ ������ ����ġ ��û
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// �������� �������� : redirect : �� ���޸� ��?, forward : ���� �۾��ߴ� ������ �ٸ������� �̾����'
		request.getRequestDispatcher("/WEB-INF/view/notice/reg.jsp").forward(request, response);
		// notice�ȿ� �ִ� detail.jsp�� ��û�ϸ鼭 ���� ����ϴ� �����(request)�� ��µ���(response)�� ����
	}
	
	// ���� �ۼ�(���)�ϸ� �����͸� ����, �Ѱܹ���
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ����ڷκ��� ���� ���� �������� �� ���� �Է¹���
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open"); // ��������
		boolean pub = false; 
		// pub�� ����üũ�� ���ϸ� null, üũ�ϸ� true�� ���⶧���� �⺻���� �������ְ�
		// üũ�� �ϸ� null�� �ƴϹǷ� true�� �ٲ���
		Collection<Part> parts = request.getParts();	// ������ �迭�� �޾ƿ�
		StringBuilder builder = new StringBuilder();
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;
			// p.getName�� file�� �̸��� �ǹ���, ������ �ƴϸ� continue�� ���� �н�
			if(p.getSize() == 0) continue;	// ������ ÷������ �ʾ����� �н�
			
	 		Part filePart = p; // �۾��⿡�� ���ε��� ������ �о���� ���� �۾�
			String fileName = filePart.getSubmittedFileName();
			InputStream fis = filePart.getInputStream(); // �Է� ��Ʈ��
			
			builder.append(fileName);
			builder.append(",");
			
			String realPath = request.getServletContext().getRealPath("/upload"); // ������θ� �����
			System.out.println(realPath);
			
			File path = new File(realPath);	 // ���������� ���� ��ΰ� �����ϴ� �� ���� Ȯ��
			if(!path.exists())	// ������ ����
				path.mkdirs();
			
			String filePath = realPath + File.separator + fileName;
			// ���ϰ�ο� ���ϸ�, separator : ���� �ý����� ��� ���� ����� ���ڷ� ����
			FileOutputStream fos = new FileOutputStream(filePath); // ��� ��Ʈ��
			
			
			byte[] buf = new byte[1024]; // Ƽ��Ǭ���� �ű�°� �ƴ϶� �絿�̷� �ۿø�
			int size = 0;
			while((size = fis.read(buf)) != -1) { // read�� 1����Ʈ�� ���� ������ �������� -1 ��ȯ
				// ���� ���� realPath�� ���
				fos.write(buf,0,size); 
				// �����͸� �׻� 1024 ��ä�����°� �ƴ϶� 1024���� �� ä��� �� ä�ŭ�� ������, ��������/�������̶� ���?
			}
			
			fos.close();
			fis.close();
		}
		if(builder.length() != 0) builder.delete(builder.length()-1, builder.length());
		// db�� ���ϸ��� �����Ҷ� �������� �ٴ� , ����
		
		if(isOpen != null)
			pub = true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setWriterId("chalbur");
		notice.setFiles(builder.toString()); // ���ϸ� ����
		
		NoticeService service = new NoticeService();
		service.insertNotice(notice);
		
		response.sendRedirect("list");
		// ���ο�������(controller)�� ��û, /admin/board/notice/reg�� reg ��� list�� ����
	
	// ------------------------------------------------------------------------------

	}
	
}
