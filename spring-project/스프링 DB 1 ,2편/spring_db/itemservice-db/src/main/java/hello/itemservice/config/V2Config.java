package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.v2.ItemQueryRepositoryV2;
import hello.itemservice.repository.v2.ItemRepositoryV2;
import hello.itemservice.repository.jpa.JpaItemRepositoryV3;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final EntityManager em;
    private final ItemRepositoryV2 itemRepositoryV2; //이건 스프링이 객체 만들어서 자동 주입됨


    @Bean
    public ItemQueryRepositoryV2 queryRepository(){
        return new ItemQueryRepositoryV2(em);
    }

    @Bean
    public ItemService itemServiceV2(){
        return new ItemServiceV2(itemRepositoryV2 , queryRepository());
    }


    //테스트 용
    @Bean
    public ItemRepository itemRepository(){
        return new JpaItemRepositoryV3(em);
    }




}
