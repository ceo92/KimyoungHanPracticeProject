package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        Member member = new Member(username , age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result"); //뷰 이름 설정
        mv.getModel().put("member" , member); // 모델 설정
        //뷰랑 모델 둘 다 설정해서 반환해줌 ㅇㅇ
        return mv;
        //프론트 컨트롤러에서 요청 파라메터 정보 전부 반환해서 Map 저장시킨 후 컨트롤러에 전달
        //즉 서블릿 처리는 프론트에서 다 하고 여기선 프론트에서 넘겨받은 username과 age를 통하여 로직 구현하는 것임
    }
}
