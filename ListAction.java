package model2.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class ListAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		BoardDAO dao = BoardDAO.getInstance();
		
		int sRow=1;
		int pageSize=20;
		List<BoardDTO> list = 
				dao.getArticles(sRow, pageSize); 	
		//view���� �� ��� �� ����
		//������� �ƴ��� Ȯ��
		
		req.setAttribute("list",list);
		return "/board2/boardList.jsp";
	}
}
