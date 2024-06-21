package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV1 implements ControllerV1 {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //비즈니스 로직 활용 부분(컨트롤러 역할)
        List<Member> members = memberRepository.findAll();
        //컨트롤러 궁극적인 목표 : 모델을 만들기 위함임  , 모델을 만들려면 비즈니스 로직이 필요 , 모델 만들었ㅇ면 뷰에 던져야지 ~


        //모델 삽입
        request.setAttribute("members" , members);
        //뷰 연결
        String viewPath = "/WEB-INF/view/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request , response);

    }
}
