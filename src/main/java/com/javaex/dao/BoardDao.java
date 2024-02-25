package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;

public class BoardDao{
	
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

	public List<BoardVo> boardSelect() {
		getConnection();
		List<BoardVo> boardList=new ArrayList<BoardVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="select b.no, b.title, b.content, u.name, b.hit, b.reg_date, b.user_no "
					+ "from board b "
					+ "left outer join users u on b.user_no=u.no";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int no=rs.getInt("b.no");
				String title=rs.getString("b.title");
				String content=rs.getString("b.content");
				String name=rs.getString("u.name");
				int hit=rs.getInt("b.hit");
				String regDate=rs.getString("b.reg_date");
				int userNo=rs.getInt("b.user_no");
				
				BoardVo boardVo=new BoardVo(no, title, content, name, hit, regDate, userNo);
				boardList.add(boardVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return boardList;
	}
	
	public int write(BoardVo boardVo) {
		getConnection();
		int count=-1;
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="insert into board "
					+ "values (null, ?, ?, 999, now(), ?);";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			

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
	
	public List<BoardVo> read(int no) {
		getConnection();
		List<BoardVo> boardList=new ArrayList<BoardVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="select b.title, b.content, u.name, b.hit, b.reg_date "
					+ "from board b "
					+ "left join users u on b.user_no=u.no "
					+ "where b.no=?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				String title=rs.getString("b.title");
				String content=rs.getString("b.content");
				String name=rs.getString("u.name");
				int hit=rs.getInt("b.hit");
				String regDate=rs.getString("b.reg_date");
				
				BoardVo boardVo=new BoardVo(no, title, content, name, hit, regDate);
				boardList.add(boardVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return boardList;
	}
	
}
