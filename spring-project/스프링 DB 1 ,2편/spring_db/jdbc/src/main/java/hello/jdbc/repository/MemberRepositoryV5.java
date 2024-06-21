package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;


/**
 * SQLExceptionTranslator 추가
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository{

    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    //DI 해줄 때 이 @Autowired 생성자의 파라메터 보고 객체를 생성해서 빈으로 등록하고 의존관계 주입 해주는 것!


    public Member save(Member member)  {
        String sql = "insert into member(member_id , money) values(? , ?)";
        int update = template.update(sql, member.getMemberId(), member.getMoney());
        return member;

    }

    public Member findById(String memberId) {
        String sql = "select * from member where member_id=?";
        return template.queryForObject(sql , memberRowMapper() , memberId);

    }

    private RowMapper<Member> memberRowMapper() {
        return (rs , rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }


    public void updateMember(String memberId, int money){
        String sql = "update member set money=? where member_id=?";
        //업데이트 된 로우 수 반환
        template.update(sql, money, memberId);
    }




    public void deleteMember(String memberId) {
        String sql = "delete from member where member_id=?";
        template.update(sql, memberId);

    }


    //Statement : sql 그대로 넣음
    //PreparedStatement : sql 파라메터 바인딩 가능하니 기능이 더 많음

    //해당 메서드를 통해 사용한 자원들 전부 닫을 수 있음 , 역순으로 가능



}
