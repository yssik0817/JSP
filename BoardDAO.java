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
   // DB에 접근해서 처리하는 작업을 모두 처리하는 객체

   /*
    * DB에 처리해야 할 일이 있으면 DAO 인스턴스를 받아서 각각 insert, delete 등 모든 작업을 모듈화 해 놓으면 다른 객체에서
    * 호출해서 사용하해서 사용
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
      int num = 0; // 글번호를 위한 변수
      if (conn != null) {
         // 시퀀스를 받아서 저장해두고
         String sql = "select boardnum_seq.nextval num from dual";
         Connection conn = null;
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            num = rs.getInt("num");
         }
         
         // insert시 받은 시퀀스 값을 사용한다
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
         pstmt.setInt(7, num); // 그룹
         pstmt.setInt(8, num); // 답글의 단계(답글, 답글의답글, 답글의답글의답글 ....)
         pstmt.setInt(9, num); // 답글의 깊이
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
	      //조회수 수정하는 작업
	      
	      PreparedStatement pstmt = null;
	      BoardDTO dto = new BoardDTO();
	      ResultSet rs = null;
	      
	      try {
	        
	         Connection conn= DBConnection.getConnection();
	      pstmt =conn.prepareStatement("Update board set Readcnt = Readcnt+1 where num=?") ;
	      pstmt.setInt(1, num);
	      
	      int i= pstmt.executeUpdate();
	        
	      if(i>0) {
	      //오라클..sql
	      String sql="select NUM,WRITER,SUBJECT,EMAIL,CONTENT,PASSWD"
	               + ",REG_DATE,READCNT,REF,RE_STEP,RE_LEVEL, ATTACHNM"
	               + " from board where num = ?";
	        
	      //connection 가져오기
	         
	         //받은 num에 해당되는 데이터 갖고오는 작업
	         pstmt=conn.prepareStatement(sql);
	         pstmt.setInt(1, num);
	         rs=pstmt.executeQuery();
	         
	         if(rs.next()){
	            //하나의 정보를 저장하는 작업
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
	      //connection 닫기 
	         return dto;
	   }
	}