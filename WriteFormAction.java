package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		//������� �ƴ��� Ȯ��
		int num=0, ref=0, re_step=1, re_level=1;
		if(req.getParameter("num")!=null) {
			num=Integer.parseInt(req.getParameter("num"));
			ref=Integer.parseInt(req.getParameter("ref"));
			re_step=Integer.parseInt(req.getParameter("re_step"));
			re_level=Integer.parseInt(req.getParameter("re_level"));
		}
		
		//view���� �� �� ����
			req.setAttribute("num", num);
			req.setAttribute("ref", ref);
			req.setAttribute("re_step",re_step );
			req.setAttribute("re_level", re_level);
		
		
		return "/board2/writeForm.jsp";
	}

}
