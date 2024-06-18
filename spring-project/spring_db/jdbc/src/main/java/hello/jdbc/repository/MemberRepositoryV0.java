package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 *  JDBC - DriverManager 사용 , Row level부터 코딩 !
 *
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id , money) values(? , ?)";

        Connection con = null; //커넥션 있어야 db와 연결하니
        PreparedStatement pstmt = null; //db에 쿼리를 날림(요청)

        try {
            con = getConnection(); //나의 h2 드라이버의 커넥션 획득 , 내가 구현한 DBConnectionUtil로 !
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1 , member.getMemberId()); //인덱스 1로 해서 1번필드에는 memberid 지정
            pstmt.setInt(2, member.getMoney()); //2로 해서 2번 필드에는 money 지정
            pstmt.executeUpdate();
            return member;

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //커넥션 연결 => 쿼리 요청 역순으로 close
            close(con , pstmt , null);
            /*pstmt.close(); //여기서 Exception 터지면 con.close() 안되니 메서드로 따로 만들어서 인수 넣어줘야됨
            con.close();*/
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection(); //커넥션 획득
            pstmt = con.prepareStatement(sql); //던질 sql 지정
            pstmt.setString(1 , memberId); //파라미터 바인딩

            rs = pstmt.executeQuery(); //값을 조회만할땐 executeQuery()로, ResultSet : Select의 결과를 담고있는 통 느낌 //
            log.info("rs = {}" , rs);
            if (rs.next()){ //rs.next를 해야 커서가 다음으로 이동해서 실제 데이터 위치부터 탐색 시작
                //첫번째 데이터 있으면 true 없으면 데이터가 아예 없으니
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money")); //데이터 존재하면 ResultSet 공간에 있던 memberId랑  , money값 가져와서 반환

                return member;
            }
            else{
                throw new NoSuchElementException("member not found member_id = "+memberId); // 더 이상의 멤버는 없어요 !
            }
        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        }finally {
            close(con , pstmt , rs);
        }
    }


    public void updateMember(String memberId , int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection(); //커넥션 연결
            pstmt = con.prepareStatement(sql); // sql 지정
            pstmt.setInt(1 , money);//바인딩 작업 해줘야됨
            pstmt.setString(2 , memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);

        }catch (SQLException e){
            log.error("db error" , e);
            throw e;
        }finally {
            close(con , pstmt , null);
        }
    }
    public void deleteMember(String memberId){
        String sql = "delete from member where member_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1 , memberId);
            pstmt.executeUpdate();
        }catch (SQLException e){
            log.error("db error" , e);
        }finally {
            close(conn , pstmt , null);
        }
    }



    //Statement : sql 그대로 넣음
    //PreparedStatement : sql 파라메터 바인딩 가능하니 기능이 더 많음

    //해당 메서드를 통해 사용한 자원들 전부 닫을 수 있음 , 역순으로 가능
    private void close(Connection con , Statement stmt , ResultSet rs){


        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error" , e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error" , e);
            }
        }
        //이렇게 로직을 구성하면 쿼리 날리는 요청 stmt 닫을때 예외 터져도 결국 catch로 잡으니 con도 정상적으로 닫힘
        //안 닫으면 커넥션 계속 유지가 되니 메모리 터짐 ㅇㅇ
        if (con != null){
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error" , e);
            }
        }
        // 이미 닫을때 예외가 터지는 거니 특별히 할 수 있는거 없음 , 로그로 찍는거밖에.
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
