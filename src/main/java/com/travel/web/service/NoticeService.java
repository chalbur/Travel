package com.travel.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.travel.web.entity.Notice;
import com.travel.web.entity.NoticeView;

	public class NoticeService {
		public int removeNoticeAll(int[] ids){ // 공지 일괄 삭제
			
			return 0;
		}
		
		public int pubNoticeAll(int[] oids, int[] cids){ // 공지 일괄 공개 , 배열을 전달할수 있음
			// 숫자를 문자열로 바꿔줘야함 / int를 컬렉션으로 바궈 전달
			List<String> oidsList = new ArrayList<>();
			for(int i=0; i<oids.length;i++) 
				oidsList.add(String.valueOf(oids[i]));
			
			List<String> cidsList = new ArrayList<>();
			for(int i=0; i<cids.length;i++) 
				cidsList.add(String.valueOf(cids[i]));
			
//			List<String> oidsList = Arrays.asList(oids); // 문자열 배열만 들어감
//			String oidsCSV = String.join(",", oids); // 단일값이나 컬레션만 들어감
//			String cidsCSV = ;
			
			
			return pubNoticeAll(oidsList, cidsList);
		}
		public int pubNoticeAll(List<String> oids, List<String> cids){
			// List를 전달할 수있음 객체이기 때문에 String int 다 보낼 수 있지만 아무거나 올수 있기때문에 String으로 한정
			
			String oidsCSV = String.join(",", oids); // 단일값이나 컬레션만 들어감
			String cidsCSV = String.join(",", cids);
			
			return pubNoticeAll(oidsCSV, cidsCSV);
		}
		public int pubNoticeAll(String oidsCSV, String cidsCSV){ 
			int result = 0;
			// 문자열로 구분 / CSV ,로 구분된 값 / 3개 함수를 다 구현하는게 아니고 하나만 구현해서 재호출함
			// open을위한 sql close를 위한 sql 두개가 있어야 함
			String sqlOpen = String.format("UPDATE NOTICE SET PUB=1 WHERE ID in (%s)", oidsCSV);
			String sqlClose = String.format("UPDATE NOTICE SET PUB=0 WHERE ID in (%s)", cidsCSV);
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				Statement stOpen = con.createStatement();
				Statement stClose = con.createStatement();
				// 값을 세팅할수 있는 능력이 포함된 Statement

				
				result += stOpen.executeUpdate(sqlOpen);
				result += stClose.executeUpdate(sqlClose);
				// 값을 확인해야할땐 result를 나누는게 맞지만 그 외는 합해도 됨

				
				stOpen.close();
				stClose.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
		
		public int insertNotice(Notice notice){ // 글쓰기, notice 객체 반환
			int result = 0; // 몇개를 삭제했는지 받는 변수
			
			
			String sql = "insert into NOTICE(TITLE, CONTENT, WRITER_ID, REGDATE, HIT, FILES) values(?,?,?,SYSDATE,0,?)";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				// 값을 세팅할수 있는 능력이 포함된 Statement
				st.setString(1, notice.getTitle());
				st.setString(2, notice.getContent());
				st.setString(3, notice.getWriterId());
				st.setString(5, notice.getFiles());
				
				result = st.executeUpdate();
				// PreparedStatement를 쓸땐 executeUpdate(sql)에서 sql을 빼아함

				
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
		public int deleteNotice(int id) { // 글 삭제
			
			return 0;
		}
		public int updateNotice(Notice notice) { // 글 수정
			
			return 0;
		}
		List<Notice> getNoticeNewestList() { 
		// 메인페이지 미니 공지사항, 댓글까진 필요 없으므로 NoticeView 대신 Notice
			
			return null;
		}
		
		
		public List<NoticeView> getNoticeList(){
			
			return getNoticeList("title", "", 1);
		}
		
		public List<NoticeView> getNoticeList(int page){
				
				return getNoticeList("title", "", page);
			}
		
		// 위에 두개와 중복됨
		public List<NoticeView> getNoticeList(String field, String query, int page){
				
			List<NoticeView> list = new ArrayList<>();
			String sql = "select * from ("+
					"select rownum NUM, N.* from (select * from NOTICE_VIEW where "+field+" like ? order by REGDATE desc) N)"+
					" where NUM between ? and ?";
			
			
			
			//데이터베이스 연결하기 위한 코드
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, "%"+query+"%");
				st.setInt(2, 1+(page-1)*10);
				st.setInt(3, page*10);
				
				ResultSet rs = st.executeQuery(); // prepared 둘중 하나만 sql을 포함해야함(넘겨야함)

				while(rs.next()){
					int id = rs.getInt("ID");
					String title = rs.getString("TITLE");
					String writerId = rs.getString("WRITER_ID");
					Date regdate = rs.getDate("REGDATE");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					int cmtCount = rs.getInt("CMT_COUNT");
					
					NoticeView notice = new NoticeView(
							id,
							title,
							writerId,
							regdate,
							hit,
							files,
							cmtCount
						);
					list.add(notice); // 여러개의 notice들을 담는 작업
				}
				rs.close();
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return list;
		}
		
		public List<NoticeView> getNoticePubList(String field, String query, int page) {
			
			List<NoticeView> list = new ArrayList<>();
			String sql = "select * from ("+
					"select rownum NUM, N.* from (select * from NOTICE_VIEW where "+field+" like ? order by REGDATE desc) N)"+
					" where PUB=1 AND NUM between ? and ?";
			
			
			
			//데이터베이스 연결하기 위한 코드
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, "%"+query+"%");
				st.setInt(2, 1+(page-1)*10);
				st.setInt(3, page*10);
				
				ResultSet rs = st.executeQuery(); // prepared 둘중 하나만 sql을 포함해야함(넘겨야함)

				while(rs.next()){
					int id = rs.getInt("ID");
					String title = rs.getString("TITLE");
					String writerId = rs.getString("WRITER_ID");
					Date regdate = rs.getDate("REGDATE");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					//String content = rs.getString("CONTENT");
					int cmtCount = rs.getInt("CMT_COUNT");
					
					NoticeView notice = new NoticeView(
							id,
							title,
							writerId,
							regdate,
							hit,
							files,
							// content
							cmtCount
						);
					list.add(notice); // 여러개의 notice들을 담는 작업
				}
				rs.close();
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return list;
		}
	
		
		public int getNoticecount() {
			
			return getNoticeCount("title", ")");
		}
		
		public int getNoticeCount(String field, String query) {
			
			int count = 0;
			
			String sql = "select COUNT(ID) COUNT from ("+
					"select rownum NUM, N.* " +
					"from (select * from NOTICE where "+field+" like ? order by REGDATE desc) N"+
					")";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, "%"+query+"%");
				
				ResultSet rs = st.executeQuery(); // 정수값 하나만 옴
				
				if(rs.next()) {	// 읽어오는 도중에 다음것이 있으면 그때는 읽어옴
					count = rs.getInt("count");
				}

				
				rs.close();
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return count;
		}
		
		public Notice getNotice(int id) {
			Notice notice = null;
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
			String sql = "select * from NOTICE where ID=?";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
				ResultSet rs = st.executeQuery();

				// 목록을 가져오는게 아님
				if(rs.next()){
					int nid = rs.getInt("ID");
					String title = rs.getString("TITLE");
					String writerId = rs.getString("WRITER_ID");
					Date regdate = rs.getDate("REGDATE");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					String content = rs.getString("CONTENT");
					notice = new Notice(
							nid,
							title,
							writerId,
							regdate,
							hit,
							files,
							content
						);
				}
				rs.close();
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return notice;

		}
		
		public Notice getNextNotice(int id) { // 이전글
			Notice notice = null;
			
			String sql = "select * from NOTICE"
					+ "where ID = "
					+ "   select ID from NOTICE"
					+ "where REGDATE > (select REGDATE from NOTICE where ID=?)"
					+ "and rownum = 1)";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
				ResultSet rs = st.executeQuery();

				// 목록을 가져오는게 아님
				if(rs.next()){
					int nid = rs.getInt("ID");
					String title = rs.getString("TITLE");
					String writerId = rs.getString("WRITER_ID");
					Date regdate = rs.getDate("REGDATE");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					String content = rs.getString("CONTENT");
					boolean pub = rs.getBoolean("pub");
					notice = new Notice(
							nid,
							title,
							writerId,
							regdate,
							hit,
							files,
							content
						);
				}
				rs.close();
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return notice;
		}
		
		public Notice getPrevNotice(int id) { // 다음글
			Notice notice = null;
			
			String sql = "select * from NOTICE"
					+ "where ID = ("
					+ "   select ID from (select * from NOTICE order by REGDATE desc)"
					+ "where REGDATE < (select REGDATE from NOTICE where ID=?)"
					+ "and rownum = 1)";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
				ResultSet rs = st.executeQuery();

				// 목록을 가져오는게 아님
				if(rs.next()){
					int nid = rs.getInt("ID");
					String title = rs.getString("TITLE");
					String writerId = rs.getString("WRITER_ID");
					Date regdate = rs.getDate("REGDATE");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					String content = rs.getString("CONTENT");
					boolean pub = rs.getBoolean("pub");
					notice = new Notice(
							nid,
							title,
							writerId,
							regdate,
							hit,
							files,
							content
						);
				}
				rs.close();
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return notice;
		}
		public int deleteNoticeAll(int[] ids) { // 일괄 삭제를 위한 서비스
			int result = 0; // 몇개를 삭제했는지 받는 변수
			
			String params = "";
			
			for(int i=0; i<ids.length;i++) {
				params += ids[i];
				if(i<ids.length-1)
					params += ",";
			}
			
			String sql = "delete NOTICE where ID in ("+params+")";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// 항상 세트로 따라다님, 코드를 실행해서 결과를 얻어옴
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				Statement st = con.createStatement();
//				PreparedStatement st = con.prepareStatement(sql);
//				st.setInt(1, id); sql에 ?가 없기때문에 사용 안함
				
				result = st.executeUpdate(sql);
				// insert, update, delete문을 쓸때 사용 ex5개를 삭제하면 5를 반환

				
				st.close();
				con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
		
}
