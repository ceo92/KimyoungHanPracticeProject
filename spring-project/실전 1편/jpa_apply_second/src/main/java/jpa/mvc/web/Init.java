package jpa.mvc.web;

import jpa.mvc.Address;
import jpa.mvc.domain.Book;
import jpa.mvc.domain.Item;
import jpa.mvc.domain.Member;
import jpa.mvc.repository.ItemRepository;
import jpa.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class Init {

  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;
  //스프링 빈으로 등록이 되어야 해당 빈에 의존관계 주입을 해줄 수가 있지 ㅇㅇ 빈 등록(@COMPONENT) => DI(@Autowired)
  @Transactional
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    Member member1 = new Member("memberA", new Address("cityA" , "distanceA" , "zipcodeA"));
    Member member2 = new Member("memberB", new Address("cityB" , "distanceB" , "zipcodeB"));
    memberRepository.save(member1);
    memberRepository.save(member2);



  }

}
