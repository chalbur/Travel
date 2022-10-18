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
		public int removeNoticeAll(int[] ids){ // ���� �ϰ� ����
			
			return 0;
		}
		
		public int pubNoticeAll(int[] oids, int[] cids){ // ���� �ϰ� ���� , �迭�� �����Ҽ� ����
			// ���ڸ� ���ڿ��� �ٲ������ / int�� �÷������� �ٱ� ����
			List<String> oidsList = new ArrayList<>();
			for(int i=0; i<oids.length;i++) 
				oidsList.add(String.valueOf(oids[i]));
			
			List<String> cidsList = new ArrayList<>();
			for(int i=0; i<cids.length;i++) 
				cidsList.add(String.valueOf(cids[i]));
			
//			List<String> oidsList = Arrays.asList(oids); // ���ڿ� �迭�� ��
//			String oidsCSV = String.join(",", oids); // ���ϰ��̳� �÷��Ǹ� ��
//			String cidsCSV = ;
			
			
			return pubNoticeAll(oidsList, cidsList);
		}
		public int pubNoticeAll(List<String> oids, List<String> cids){
			// List�� ������ ������ ��ü�̱� ������ String int �� ���� �� ������ �ƹ��ų� �ü� �ֱ⶧���� String���� ����
			
			String oidsCSV = String.join(",", oids); // ���ϰ��̳� �÷��Ǹ� ��
			String cidsCSV = String.join(",", cids);
			
			return pubNoticeAll(oidsCSV, cidsCSV);
		}
		public int pubNoticeAll(String oidsCSV, String cidsCSV){ 
			int result = 0;
			// ���ڿ��� ���� / CSV ,�� ���е� �� / 3�� �Լ��� �� �����ϴ°� �ƴϰ� �ϳ��� �����ؼ� ��ȣ����
			// open������ sql close�� ���� sql �ΰ��� �־�� ��
			String sqlOpen = String.format("UPDATE NOTICE SET PUB=1 WHERE ID in (%s)", oidsCSV);
			String sqlClose = String.format("UPDATE NOTICE SET PUB=0 WHERE ID in (%s)", cidsCSV);
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				Statement stOpen = con.createStatement();
				Statement stClose = con.createStatement();
				// ���� �����Ҽ� �ִ� �ɷ��� ���Ե� Statement

				
				result += stOpen.executeUpdate(sqlOpen);
				result += stClose.executeUpdate(sqlClose);
				// ���� Ȯ���ؾ��Ҷ� result�� �����°� ������ �� �ܴ� ���ص� ��

				
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
		
		public int insertNotice(Notice notice){ // �۾���, notice ��ü ��ȯ
			int result = 0; // ��� �����ߴ��� �޴� ����
			
			
			String sql = "insert into NOTICE(TITLE, CONTENT, WRITER_ID, REGDATE, HIT, FILES) values(?,?,?,SYSDATE,0,?)";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				// ���� �����Ҽ� �ִ� �ɷ��� ���Ե� Statement
				st.setString(1, notice.getTitle());
				st.setString(2, notice.getContent());
				st.setString(3, notice.getWriterId());
				st.setString(5, notice.getFiles());
				
				result = st.executeUpdate();
				// PreparedStatement�� ���� executeUpdate(sql)���� sql�� ������

				
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
		public int deleteNotice(int id) { // �� ����
			
			return 0;
		}
		public int updateNotice(Notice notice) { // �� ����
			
			return 0;
		}
		List<Notice> getNoticeNewestList() { 
		// ���������� �̴� ��������, ��۱��� �ʿ� �����Ƿ� NoticeView ��� Notice
			
			return null;
		}
		
		
		public List<NoticeView> getNoticeList(){
			
			return getNoticeList("title", "", 1);
		}
		
		public List<NoticeView> getNoticeList(int page){
				
				return getNoticeList("title", "", page);
			}
		
		// ���� �ΰ��� �ߺ���
		public List<NoticeView> getNoticeList(String field, String query, int page){
				
			List<NoticeView> list = new ArrayList<>();
			String sql = "select * from ("+
					"select rownum NUM, N.* from (select * from NOTICE_VIEW where "+field+" like ? order by REGDATE desc) N)"+
					" where NUM between ? and ?";
			
			
			
			//�����ͺ��̽� �����ϱ� ���� �ڵ�
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, "%"+query+"%");
				st.setInt(2, 1+(page-1)*10);
				st.setInt(3, page*10);
				
				ResultSet rs = st.executeQuery(); // prepared ���� �ϳ��� sql�� �����ؾ���(�Ѱܾ���)

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
					list.add(notice); // �������� notice���� ��� �۾�
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
			
			
			
			//�����ͺ��̽� �����ϱ� ���� �ڵ�
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, "%"+query+"%");
				st.setInt(2, 1+(page-1)*10);
				st.setInt(3, page*10);
				
				ResultSet rs = st.executeQuery(); // prepared ���� �ϳ��� sql�� �����ؾ���(�Ѱܾ���)

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
					list.add(notice); // �������� notice���� ��� �۾�
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
				
				ResultSet rs = st.executeQuery(); // ������ �ϳ��� ��
				
				if(rs.next()) {	// �о���� ���߿� �������� ������ �׶��� �о��
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
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
				ResultSet rs = st.executeQuery();

				// ����� �������°� �ƴ�
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
		
		public Notice getNextNotice(int id) { // ������
			Notice notice = null;
			
			String sql = "select * from NOTICE"
					+ "where ID = "
					+ "   select ID from NOTICE"
					+ "where REGDATE > (select REGDATE from NOTICE where ID=?)"
					+ "and rownum = 1)";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
				ResultSet rs = st.executeQuery();

				// ����� �������°� �ƴ�
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
		
		public Notice getPrevNotice(int id) { // ������
			Notice notice = null;
			
			String sql = "select * from NOTICE"
					+ "where ID = ("
					+ "   select ID from (select * from NOTICE order by REGDATE desc)"
					+ "where REGDATE < (select REGDATE from NOTICE where ID=?)"
					+ "and rownum = 1)";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
				ResultSet rs = st.executeQuery();

				// ����� �������°� �ƴ�
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
		public int deleteNoticeAll(int[] ids) { // �ϰ� ������ ���� ����
			int result = 0; // ��� �����ߴ��� �޴� ����
			
			String params = "";
			
			for(int i=0; i<ids.length;i++) {
				params += ids[i];
				if(i<ids.length-1)
					params += ",";
			}
			
			String sql = "delete NOTICE where ID in ("+params+")";
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
				
			
			// �׻� ��Ʈ�� ����ٴ�, �ڵ带 �����ؼ� ����� ����
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "TRAVEL", "5320");
				Statement st = con.createStatement();
//				PreparedStatement st = con.prepareStatement(sql);
//				st.setInt(1, id); sql�� ?�� ���⶧���� ��� ����
				
				result = st.executeUpdate(sql);
				// insert, update, delete���� ���� ��� ex5���� �����ϸ� 5�� ��ȯ

				
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
