package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public void getConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url, "web", "web");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}//getConnection()

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}//close()

	public List<GuestVo> guestRead() {
		getConnection();
		List<GuestVo> guestList=new ArrayList<GuestVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="select no, name,  "
					+ "	   content, reg_date "
					+ "from guestbook";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int no=rs.getInt("no");
				String name=rs.getString("name");
				String content=rs.getString("content");
				String regDate=rs.getString("reg_date");
				
				GuestVo guestVo=new GuestVo(no, name, content, regDate);
				//리스트에 주소 추가
				guestList.add(guestVo);
				//System.out.println(guestVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return guestList;
	}
	
	public int guestWrite(GuestVo guestVo) {
		getConnection();
		int count=-1;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="insert into guestbook "
					+ "values(null, ?, ?, ?, curdate())";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			
			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록 되었습니다.");


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	public int delete(int no, String password) {
		getConnection();
		
		int count=-1;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="delete from guestbook "
					+ "where no=? and password=?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			
			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제 되었습니다.");


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

}