package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;

	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("실행");

		String action=request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {
			System.out.println("list:등록폼");

			GuestDao guestDao=new GuestDao();
			
			List<GuestVo> guestList = guestDao.guestRead();
			
			request.setAttribute("guestList", guestList);
			
			//jsp한테 html그리기 응답
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		}else if("write".equals(action)) {
			System.out.println("write:등록하기");
			
			//int no=Integer.parseInt(request.getParameter("no"));
			String name=request.getParameter("name");
			String password=request.getParameter("pw");
			String content=request.getParameter("content");
			String regDate=request.getParameter("reg_date");
			
			System.out.println(password);
			GuestVo GuestVo=new GuestVo(name, password, content, regDate);
			
			GuestDao guestDao=new GuestDao();
			
			guestDao.guestWrite(GuestVo);
			
			WebUtil.redirect(request, response, "/mysite3/gbc?action=list");
			
		}else if("deleteform".equals(action)) {
			System.out.println("deleteform:삭제폼");
			
			int no=Integer.parseInt(request.getParameter("no"));
			
			request.setAttribute("no", no);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		}
		
		else if("delete".equals(action)) {
			System.out.println("delete:삭제");
			int no=Integer.parseInt(request.getParameter("no"));
			String password=request.getParameter("pw");
			
			GuestDao guestDao=new GuestDao();
			
			guestDao.delete(no, password);
			
			WebUtil.redirect(request, response, "/mysite3/gbc?action=list");
		}
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
