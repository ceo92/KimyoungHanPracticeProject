package hello.itemservice.web;

public enum LastEducation {
    UNIV("대졸") , HIGH("고졸") , MIDDLE("중졸");
    //상수명(상수값)
    private final String finalEdu;
    LastEducation(String finalEdu){
        this.finalEdu = finalEdu;
    }
    public String getFinalEdu(){
        return finalEdu;
    } //getter을 해야 private 변수인 discription에 접근할 수 있음

}
