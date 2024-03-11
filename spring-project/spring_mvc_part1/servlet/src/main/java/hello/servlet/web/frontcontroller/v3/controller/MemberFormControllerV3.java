package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
        //정적인 html 폼 페이지일 뿐이니 그 정적인 페이지를 보여주는(랜더링) jsp의 논리적 이름만 반환
    }
}
