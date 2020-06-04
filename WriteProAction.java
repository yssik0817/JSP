package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class WriteProAction implements CommandAction {

   @Override
   public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
      //�ѱ� Ȯ���۾��� ���� utf-8�� ��ȯ
      req.setCharacterEncoding("utf-8");
      //writeForm���� ������ ������ ��� �ޱ�
      BoardDTO dto = new BoardDTO();
      //num, ref, re_step, re_level �޾ƾߵȴ�.
      //writer, subject, content, passwd, attachNm,email
      dto.setNum(Integer.parseInt(req.getParameter("num")));
      dto.setRef(Integer.parseInt(req.getParameter("ref")));
      dto.setRe_step(Integer.parseInt(req.getParameter("re_step")));
      dto.setRe_level(Integer.parseInt(req.getParameter("re_level")));
      dto.setWriter(req.getParameter("writer"));
      dto.setSubject(req.getParameter("subject"));
      dto.setContent(req.getParameter("content"));
      dto.setPasswd(req.getParameter("passwd"));
      dto.setEmail(req.getParameter("email"));
      dto.setAttachNm(req.getParameter("attachNm"));
      //DAO�� ���ؼ� ���� �����������ϱ�
       //DAO�� ���� �ν��Ͻ� �޾ƿ���
       BoardDAO dao = BoardDAO.getInstance();
      dao.boardWrite(dto);
   

      return "/board2/writePro.jsp";
   }

}