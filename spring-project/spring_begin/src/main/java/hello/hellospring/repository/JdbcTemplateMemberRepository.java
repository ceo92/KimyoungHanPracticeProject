package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        // 이렇게 하면 name , member , id를 이용하여 insert 문 만들어줌 ㅇㅇ
        // 위에서 키랑 테이블 지정하고 밑에 일반 칼럼들 put으로 쭉쭉 진열하는 것임

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        //execute 해서 key를 받고
        member.setId(key.longValue());
        //그걸 통해 id값을 넣어줌 , document 에 자세히 나와있음 참조 ㄴ나중에
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> query = jdbcTemplate.query("select * from member where id=?", memberRowMapper() , id);
        return query.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> query = jdbcTemplate.query("select * from member where name=?", memberRowMapper() , name);
        //쿼리 날리면 결과를 RowMapper을 통해 매핑 후 return 함
        return query.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member" ,memberRowMapper() );
    }


    //객체 생성해주는 메서드임
    private RowMapper<Member> memberRowMapper(){
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id")); //ResultSet이 여기로 넘어오네
                member.setName(rs.getString("name"));
                return member;
            }
        };
    }
}
