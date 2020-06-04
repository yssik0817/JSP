package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;

public class DeleteProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {

			int num = Integer.parseInt(req.getParameter("num"));
			//DAO ��������
			BoardDAO dao = BoardDAO.getInstance();
			//DAO �޼ҵ� ����
			dao.deleteArticle(num);
			//�����ϸ� boardList.jsp
		
		return "/board2/deletePro.jsp";
	}

}
