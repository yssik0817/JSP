package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class UpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {

		
	      //writeForm���� ������ ������ ��� �ޱ�
	      BoardDTO dto = new BoardDTO();
	      //num, ref, re_step, re_level �޾ƾߵȴ�.
	      //writer, subject, content, passwd, attachNm,email
	      dto.setNum(Integer.parseInt(req.getParameter("num")));
	      dto.setSubject(req.getParameter("subject"));
	      dto.setContent(req.getParameter("content"));
	      dto.setPasswd(req.getParameter("passwd"));
	      dto.setEmail(req.getParameter("email"));
	      dto.setAttachNm(req.getParameter("attachNm"));

	      //DAO�� ���ؼ� ���� �����������ϱ�
	       //DAO�� ���� �ν��Ͻ� �޾ƿ���
	       BoardDAO dao = BoardDAO.getInstance();
	       //DAO�� �ش� ������ �����ϴ� ������ �����
		
				//�� ������ ����� �� 
	       		dao.boardWrite(dto);
				//���� �������� �̵���Ŵ
		
		return "/board2/updatePro.jsp";
	}

}
