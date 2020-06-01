package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import common.dbutil.DBConnection;

public class BoardDAO {
   // DAO : Data Access Object
   // DB�� �����ؼ� ó���ϴ� �۾��� ��� ó���ϴ� ��ü

   /*
    * DB�� ó���ؾ� �� ���� ������ DAO �ν��Ͻ��� �޾Ƽ� ���� insert, delete �� ��� �۾��� ���ȭ �� ������ �ٸ� ��ü����
    * ȣ���ؼ� ������ؼ� ���
    */
   Connection conn = null;

   private static BoardDAO instance = new BoardDAO();

   public static BoardDAO getInstance() {
      return instance;
   }

   public int boardWrite(BoardDTO dto) throws NamingException, SQLException {
      conn = DBConnection.getConnection();
      int cnt = 0;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int num = 0; // �۹�ȣ�� ���� ����
      if (conn != null) {
         // �������� �޾Ƽ� �����صΰ�
         String sql = "select boardnum_seq.nextval num from dual";
         Connection conn = null;
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            num = rs.getInt("num");
         }
         
         // insert�� ���� ������ ���� ����Ѵ�
         sql = "insert into board(NUM,WRITER,SUBJECT,EMAIL,";
         sql += "CONTENT,PASSWD,REG_DATE,READCNT,REF,RE_STEP,RE_LEVEL, attachnm)";
         sql += " values(?,?,?,?,?,?,"; // NUM,WRITER,SUBJECT,EMAIL,CONTENT,PASSWD
         sql += "sysdate,0,?,?,?,?)";

         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         pstmt.setString(2, dto.getWriter());
         pstmt.setString(3, dto.getSubject());
         pstmt.setString(4, dto.getEmail());
         pstmt.setString(5, dto.getContent());
         pstmt.setString(6, dto.getPasswd());
         pstmt.setInt(7, num); // �׷�
         pstmt.setInt(8, num); // ����� �ܰ�(���, ����Ǵ��, ����Ǵ���Ǵ�� ....)
         pstmt.setInt(9, num); // ����� ����
         pstmt.setString(10, dto.getAttachNm());

         cnt = pstmt.executeUpdate();
      }
      if (rs != null)
         rs.close();
      if (pstmt != null)
         pstmt.close();
      if (conn != null)
         conn.close();
      return cnt;
   }

   public List<BoardDTO> getArticles(int sRow, int pageSize) throws NamingException, SQLException {
      String sql = "select num,writer,subject,email,content,";
      sql += "passwd,reg_date,readcnt,ref,re_step,re_level, attachnm ";
      sql += "from board ";
      sql += "where rownum between 1 and 20 ";
      sql += "order by ref desc";

      Connection conn = null;
      conn = DBConnection.getConnection();
      List<BoardDTO> articles = new ArrayList<BoardDTO>(20);
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
         BoardDTO dto = new BoardDTO();
         dto.setNum(rs.getInt("num"));
         dto.setRef(rs.getInt("ref"));
         dto.setRe_step(rs.getInt("re_step"));
         dto.setRe_level(rs.getInt("re_level"));
         dto.setReadcnt(rs.getInt("readcnt"));
         dto.setWriter(rs.getString("writer"));
         dto.setSubject(rs.getString("subject"));
         dto.setEmail(rs.getString("email"));
         dto.setContent(rs.getString("content"));
         dto.setPasswd(rs.getString("passwd"));
         dto.setReg_date(rs.getString("reg_date"));
         dto.setAttachNm(rs.getString("attachNm"));
         articles.add(dto);
      }
      if (rs != null)
         rs.close();
      if (pstmt != null)
         pstmt.close();
      if (conn != null)
         conn.close();

      return articles;

   }

   public BoardDTO getArticle(int num) throws NamingException/* , SQLException */{
	      //��ȸ�� �����ϴ� �۾�
	      
	      PreparedStatement pstmt = null;
	      BoardDTO dto = new BoardDTO();
	      ResultSet rs = null;
	      
	      try {
	        
	         Connection conn= DBConnection.getConnection();
	      pstmt =conn.prepareStatement("Update board set Readcnt = Readcnt+1 where num=?") ;
	      pstmt.setInt(1, num);
	      
	      int i= pstmt.executeUpdate();
	        
	      if(i>0) {
	      //����Ŭ..sql
	      String sql="select NUM,WRITER,SUBJECT,EMAIL,CONTENT,PASSWD"
	               + ",REG_DATE,READCNT,REF,RE_STEP,RE_LEVEL, ATTACHNM"
	               + " from board where num = ?";
	        
	      //connection ��������
	         
	         //���� num�� �ش�Ǵ� ������ ������� �۾�
	         pstmt=conn.prepareStatement(sql);
	         pstmt.setInt(1, num);
	         rs=pstmt.executeQuery();
	         
	         if(rs.next()){
	            //�ϳ��� ������ �����ϴ� �۾�
	            dto.setNum(rs.getInt("num"));
	            dto.setRef(rs.getInt("ref"));
	            dto.setRe_step(rs.getInt("Re_step"));
	            dto.setRe_level(rs.getInt("Re_level"));
	            dto.setReadcnt(rs.getInt("Readcnt"));
	            dto.setWriter(rs.getString("Writer"));
	            dto.setSubject(rs.getString("subject"));
	            dto.setContent(rs.getString("content"));
	            dto.setEmail(rs.getString("email"));
	            dto.setReg_date(rs.getString("reg_date"));
	            dto.setPasswd(rs.getString("passwd"));
	            dto.setAttachNm(rs.getString("AttachNm"));
	         }
	      }
	      if(rs!=null) rs.close();
	      if(pstmt!=null)pstmt.close();
	      if (conn!=null)conn.close();
	      }catch(SQLException e) {
	         e.printStackTrace();
	      }
	      //connection �ݱ� 
	         return dto;
	   }
	}