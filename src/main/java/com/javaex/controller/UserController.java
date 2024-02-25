package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//user 실행되는지 확인
		System.out.println("UserController");

		//user에서 업무 구분
		String action=request.getParameter("action");
		System.out.println(action);

		if("joinform".equals(action)) {
			System.out.println("user > joinform");

			//회원가입 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		}else if("join".equals(action)) {
			System.out.println("user > join");

			String id=request.getParameter("id");
			String password=request.getParameter("pw");
			String name=request.getParameter("name");
			String gender=request.getParameter("gender");

			System.out.println(id);
			System.out.println(password);

			UserVo userVo=new UserVo(id, password, name, gender);
			System.out.println(userVo);

			UserDao userDao=new UserDao();

			userDao.insertUser(userVo);

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		}else if("loginform".equals(action)){
			System.out.println("user > loginform");
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}else if("login".equals(action)){
			System.out.println("user > login");

			String id=request.getParameter("id");
			String password=request.getParameter("pw");

			UserVo userVo=new UserVo(id, password);

			UserDao userDao=new UserDao();
			userDao.selectUserByIdPw(userVo); // id pw

			UserVo authUser=userDao.selectUserByIdPw(userVo); //no name

			if(authUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				session.setAttribute("userVo", userVo);

				WebUtil.redirect(request, response, "/mysite3/main");
			}else {
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite3/user?action=loginform");
			}

		}else if("logout".equals(action)) {
			System.out.println("user > logout");

			HttpSession session = request.getSession();
			session.invalidate();

			WebUtil.redirect(request, response, "/mysite3/main");

		}else if("updateform".equals(action)) {
			System.out.println("user > updateform");

			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		}else if("update".equals(action)) {
			System.out.println("user > update");

			int no = Integer.parseInt(request.getParameter("no"));
			String id = request.getParameter("id"); 
			String name = request.getParameter("name"); 
			String password = request.getParameter("pw");
			String gender = request.getParameter("gender");

			UserVo userVo=new UserVo(no, id, password, name, gender);
			
			UserDao userDao=new UserDao();
			userDao.personUpdate(userVo);

			HttpSession session = request.getSession();

			session.setAttribute("authUser", userVo);

			WebUtil.redirect(request, response, "/mysite3/main");


		}else {
			System.out.println("action값을 다시 확인해 주세요");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
