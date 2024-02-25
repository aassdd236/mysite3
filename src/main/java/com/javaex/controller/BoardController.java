package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("BoardController");

		String action=request.getParameter("action");
		System.out.println(action);

		if("list".equals(action)) {
			System.out.println("user > list");

			BoardDao boardDao=new BoardDao();

			List<BoardVo> boardList=boardDao.boardSelect();
			System.out.println(boardList);

			request.setAttribute("boardList", boardList);


			//회원가입 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		}else if("writeform".equals(action)) {
			System.out.println("user > writeform");
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("write".equals(action)) {
			System.out.println("user > write");
			
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			
			HttpSession session = request.getSession();
            UserVo authUser = (UserVo)session.getAttribute("authUser");
            int userNo = authUser.getNo();
			
			BoardVo boardVo=new BoardVo(title, content, userNo);
			BoardDao boardDao=new BoardDao();
			
			boardDao.write(boardVo);
			
			WebUtil.redirect(request, response, "/mysite3/bc?action=list");
			
		}else if("read".equals(action)) {
			System.out.println("user > read");
			
			BoardDao boardDao=new BoardDao();
			
			int no=Integer.parseInt(request.getParameter("no"));
			
			List<BoardVo> boardList=boardDao.read(no);
			
			request.setAttribute("board", boardList.get(0));
			System.out.println(no);
			
			System.out.println(boardList);
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("updateform".equals(action)) {
			System.out.println("user > updateform");
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
