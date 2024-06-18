package jpql;

public class MemberDTO {

    private Member member;
    private Team team;

    @Override
    public String toString() {
        return "MemberDTO{" +
                "member=" + member +
                ", team=" + team +
                '}';
    }

    public MemberDTO(Member member, Team team) {
        this.member = member;
        this.team = team;
    }

    public MemberDTO() {
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
