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

//첨부파일을 위해 form에 enctype="multipart/form-data"을 썼더니 인코딩방식이 바뀌어서
//해결하기위한 추가작업, 멀티파트로 인코딩을 보내면 서버에서도 멀티파트로 받아야하므로 설정을 해줘야함
@MultipartConfig( 
	fileSizeThreshold=1024*1024, // 전송하는 데이터가 1mb가 넘어가는 경우 서버 말고 디스크를 쓰자
	maxFileSize=1024*1024*50, // 한번에 보내는 데이터양 제한, 파일 하나의 사이즈(50mb)
	maxRequestSize=1024*1024*5*50 // 파일 전체의 용량 (250mb)
)
@WebServlet("/notice/reg")// 루트에서 인덱스를 요청하면 여기서 받음
public class NoticeRegController extends HttpServlet{
	// 글쓰기를 누를때 페이치 요청
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// 서블릿에서 서블릿으로 : redirect : 걍 전달만 함?, forward : 현재 작업했던 내용을 다른곳에서 이어받음'
		request.getRequestDispatcher("/WEB-INF/view/notice/reg.jsp").forward(request, response);
		// notice안에 있는 detail.jsp를 요청하면서 현재 사용하는 저장소(request)와 출력도구(response)를 공유
	}
	
	// 글을 작성(등록)하면 데이터를 전송, 넘겨받음
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자로부터 제목 내용 공개여부 등 값을 입력받음
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open"); // 공개여부
		boolean pub = false; 
		// pub은 공개체크를 안하면 null, 체크하면 true로 오기때문에 기본값을 설정해주고
		// 체크를 하면 null이 아니므로 true로 바꿔줌
		Collection<Part> parts = request.getParts();	// 파일을 배열로 받아옴
		StringBuilder builder = new StringBuilder();
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;
			// p.getName은 file의 이름을 의미함, 파일이 아니면 continue를 통해 패스
			if(p.getSize() == 0) continue;	// 파일을 첨부하지 않았으면 패스
			
	 		Part filePart = p; // 글쓰기에서 업로드한 파일을 읽어오기 위한 작업
			String fileName = filePart.getSubmittedFileName();
			InputStream fis = filePart.getInputStream(); // 입력 스트림
			
			builder.append(fileName);
			builder.append(",");
			
			String realPath = request.getServletContext().getRealPath("/upload"); // 물리경로를 얻어줌
			System.out.println(realPath);
			
			File path = new File(realPath);	 // 물리적으로 얻은 경로가 존재하는 지 여부 확인
			if(!path.exists())	// 없으면 생성
				path.mkdirs();
			
			String filePath = realPath + File.separator + fileName;
			// 파일경로와 파일명, separator : 현재 시스템의 경로 구분 방법을 문자로 제공
			FileOutputStream fos = new FileOutputStream(filePath); // 출력 스트림
			
			
			byte[] buf = new byte[1024]; // 티스푼으로 옮기는게 아니라 양동이로 퍼올림
			int size = 0;
			while((size = fis.read(buf)) != -1) { // read는 1바이트씩 읽음 데이터 다읽으면 -1 반환
				// 읽은 값을 realPath에 출력
				fos.write(buf,0,size); 
				// 데이터를 항상 1024 꽉채워오는게 아니라 1024보다 덜 채우면 덜 채운만큼만 가져옴, 고정길이/가변길이랑 비슷?
			}
			
			fos.close();
			fis.close();
		}
		if(builder.length() != 0) builder.delete(builder.length()-1, builder.length());
		// db에 파일명을 저장할때 마지막에 붙는 , 제거
		
		if(isOpen != null)
			pub = true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setWriterId("chalbur");
		notice.setFiles(builder.toString()); // 파일명 저장
		
		NoticeService service = new NoticeService();
		service.insertNotice(notice);
		
		response.sendRedirect("list");
		// 새로운페이지(controller)를 요청, /admin/board/notice/reg의 reg 대신 list가 들어감감
	
	// ------------------------------------------------------------------------------

	}
	
}
