package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class ModelView {

    private String viewName;//컨트롤러부터 반환받은 논리적 뷰 이름임(new-form같은)
    private Map<String , Object> model = new HashMap<>();
    //Map<모델의 이름 , 모델>


    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public ModelView(String viewName) {
        this.viewName = viewName;
    }


}
