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
   //DAO: Data Access Object
   //DB접근해서 처리하는 작업을 모두 처리하는 객체
   
   /*
      DB처리해야 할 일이 있으면 DAO 인스턴스를 받아서
      각 각 insert, delete 등 모든 작업을 
      모듈화해 놓으면 다른 객체에서 호출해서 사용
   */
   
  
   
   private static BoardDAO instance = new BoardDAO();
   
   public static BoardDAO getInstance() {
      return instance;
   }
   public int boardWrite(BoardDTO dto) throws NamingException, SQLException {
      Connection conn= DBConnection.getConnection();
      int cnt=0;
      PreparedStatement pstmt=null;
      ResultSet rs= null;
      
      //글번호를 위한 변수
      int num=dto.getNum();
      int number=0;
      int ref=dto.getRef();
      int re_step=dto.getRe_step();
      int re_level=dto.getRe_level();
         if(conn!=null) {
            String sql="";
               //새로운 글번호 만들기
         //시퀀스를 받아서 저장해두고  insert시 
            sql ="select max(num)+1 num from board";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            if(rs.next()) {
               number=rs.getInt("num");
               }
           if(num==0) {
           num=number;
            ref=num;
            re_step=1;
            re_level=1;
           }else if (num!=0) {
              num=number;
              re_step+=1;
               re_level+=1;
           }
         //시퀀스 받은 값을 사용한다.
            sql="insert into board(NUM,"
            + "WRITER,SUBJECT,EMAIL,CONTENT,PASSWD,REG_DATE,READCNT,"
            + "REF,RE_STEP,RE_LEVEL,ATTACHNM )";
            sql+="values(?,?,"
            + "?,?,?,?,sysdate,0,"
            + "?, ?, ?,?)";
            
         pstmt=conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         pstmt.setString(2, dto.getWriter());
         pstmt.setString(3, dto.getSubject());
         pstmt.setString(4, dto.getEmail());
         pstmt.setString(5, dto.getContent());
         pstmt.setString(6, dto.getPasswd());
         pstmt.setInt(7, ref);   //그룹
         pstmt.setInt(8, re_step);   //답글의 단계
         pstmt.setInt(9, re_level);   //답글의 깊이
         pstmt.setString(10, dto.getAttachNm());   
         cnt = pstmt.executeUpdate();
         
         
      }
      if(rs!=null) rs.close();
      if(pstmt!=null)pstmt.close();
      if(conn!=null)conn.close();
      return cnt;
   }
   
   public List<BoardDTO> getArticles(int sRow, int pageSize) throws NamingException, SQLException{
      String sql = "select a.* from"+
             " (select rownum rr,rboard.* from"+
             "(select NUM, WRITER, SUBJECT,EMAIL, CONTENT, PASSWD, REG_DATE,"+
             " READCNT, REF, RE_STEP, RE_LEVEL, ATTACHNM "+
            "from board order by ref desc, re_step asc)rboard )a "+
             "where a.rr BETWEEN ? and ? ";
       
         Connection conn= DBConnection.getConnection();
         List<BoardDTO> articles = new ArrayList<BoardDTO>(20);
         PreparedStatement pstmt=conn.prepareStatement(sql);
         
         pstmt.setInt(1, sRow);
         pstmt.setInt(2, pageSize);
         
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()){
            //하나의 정보를 저장하는 작업
            BoardDTO dto = new BoardDTO();
            dto.setRr(rs.getInt("rr"
                  + ""
                  + ""
                  + ""
                  + ""));
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
            articles.add(dto);
            
         }
         if(rs!=null) rs.close();
         if(pstmt!=null)pstmt.close();
         if (conn!=null)conn.close();
   
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
         System.out.println(dto.getRef());
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
   
   public int boardUpdate(BoardDTO dto) {
      int r= 0;
      //DBConnection
      //prepaeredstatement
      //실행
      System.out.println(dto.getSubject());
       PreparedStatement pstmt = null;
       try {
          String sql = "update board set " + 
                "SUBJECT=? ," + 
                "EMAIL=? ," + 
                "CONTENT=? ," + 
                "PASSWD=? ," + 
                "ATTACHNM=? " + 
                " where num = ? ";
         Connection conn= DBConnection.getConnection();
         if(conn!=null) {
            System.out.println("연결성공");
         
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, dto.getSubject());
           pstmt.setString(2, dto.getEmail());
           pstmt.setString(3, dto.getContent());
           pstmt.setString(4, dto.getPasswd());
           pstmt.setString(5, dto.getAttachNm());
           pstmt.setInt(6, dto.getNum());
           r = pstmt.executeUpdate();}
         if(pstmt!=null)pstmt.close();
          if (conn!=null)conn.close();
      } catch (NamingException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
       
       return r;
   }
   public int deleteArticle(int num) {
      int r = 0;
      //DB연결
      //쿼리만들기
      //pstmt만들기
      //실행하고
      //return
      PreparedStatement pstmt = null;
      Connection conn = null;
       try {
         conn= DBConnection.getConnection();
         String sql = "delete from board where num = ?";
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, num);
           r = pstmt.executeUpdate();
      } catch (NamingException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
       try {
          
          if(pstmt!=null)pstmt.close();
          if (conn!=null)conn.close();
       }catch (SQLException e) {
            e.printStackTrace();
         }
      return r;
   }
   
   	public int getAllCount() throws NamingException {
   		//Conn, pstmt, rs
   		PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int allCnt=0;
        try {
            conn= DBConnection.getConnection();
            String sql="select count(num) cnt from board";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	allCnt=rs.getInt("cnt");
            }
        
          //rs, pstmt, conn 닫기
        	 if(rs!=null) {rs.close();}
             if(pstmt!=null){pstmt.close();};
             if(conn!=null){conn.close();};
          }catch (SQLException e) {
               e.printStackTrace();
            }
         return allCnt;
      }
	}