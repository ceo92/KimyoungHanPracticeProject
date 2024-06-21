package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) { //지원하는지 검증
        return Item.class.isAssignableFrom(clazz);
        // item = clazz 여부 따짐 , class로 넘어오는 타입과 item타입 같은지
        // item == subItem
        // Item 클래스 및 자식 클래스까지 다 커버할 수 있으니 isAssignableFrom이 좋음
    }

    @Override
    public void validate(Object target, Errors errors) { //검증 실행
        //target은 검증할 객체가 넘어옴 즉 Object 타입이니 Item으로 캐스팅해야됨
        //Errors는 BindingResult의 부모이니 BindingResult 넣어줄 수 있음
        Item item = (Item) target;

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName" , "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price" , "range" , new Object[]{1000 , 10000} , null);

        }

        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity" , "max" , new Object[]{9999} , null);

        }

        //특정 필드가 아닌 복합 룰 검증
        // 특정 필드가 아닌 전역 오류이니 그냥 ObjectError로 처리하고 특정 필드 지정 안 해도 됨 전역 에러이니 !
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject( "totalPriceMin" , new Object[]{10000 , resultPrice} , null);
            }
        }
        //에러가 있으면 ! 하는 직관적인 로직임 NOT 연산자 필요 없음

    }
}
