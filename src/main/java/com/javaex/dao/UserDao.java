package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

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

	public int insertUser(UserVo userVo) {
		int count=-1;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="insert into users "
					+ "values(null, ?, ?, ?, ?)";
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

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

	public UserVo selectUserByIdPw(UserVo userVo) {
		this.getConnection();
		UserVo authUser = null;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="select no, name, id, password, gender "
					+ "from users "
					+ "where id=? and password=?";
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int no=rs.getInt("no");
				String name=rs.getString("name");
				String id=rs.getString("id");
				String password=rs.getString("password");
				String gender=rs.getString("gender");
				authUser=new UserVo();
				authUser.setNo(no);
				authUser.setName(name);
				authUser.setId(id);
				authUser.setPw(password);
				authUser.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return authUser;
	}

	public int personUpdate(UserVo userVo) {
		this.getConnection();
		int count=-1;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query="update users "
					+ "set password=? ,name = ?, gender=? "
					+ "where no=?";
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPw());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}



}
