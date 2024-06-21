package happy.core.order;


import happy.core.AppConfig;
import happy.core.annotation.MainDiscountPolicy;
import happy.core.discount.DiscountPolicy;
import happy.core.member.Member;
import happy.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository; //주문 시 db에 저장된 회원 정보 가져와야하니
    private final DiscountPolicy discountPolicy;



    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //1. 회원 등급 검증은 애초에 DiscountPolicy에서 함
        Member member = memberRepository.findById(memberId); //2. 회원 정보 반환 완료
        int discountPrice = discountPolicy.discount(member, itemPrice);
        Order order = new Order(memberId , itemName, itemPrice ,discountPrice);
        return order;
    }
}
