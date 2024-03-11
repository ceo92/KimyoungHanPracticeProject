package hello.itemservice.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ItemRepositoryImplTest {
    ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();
    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item1 = new Item("IPhone 12" , 1000000 , 2);
        Item item2 = new Item("Galaxy S24" , 1300000 , 4);

        //when
        itemRepository.save(item1);
        itemRepository.save(item2);
        Item result1 = itemRepository.findById(item1.getId());
        Item result2 = itemRepository.findById(item2.getId());

        //then
        org.assertj.core.api.Assertions.assertThat(item1).isSameAs(result2);
        org.assertj.core.api.Assertions.assertThat(item2).isSameAs(result2);


    }



    @Test
    void findAll() {
        Item item1 = new Item("IPhone 12" , 1000000 , 2);
        Item item2 = new Item("Galaxy S24" , 1300000 , 4);
        Item item3 = new Item("Galaxy A14" , 500000 , 1);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        org.assertj.core.api.Assertions.assertThat(itemRepository.findAll().size()).isEqualTo(3);

    }

    @Test
    void update() {
        //given
        Item item2 = new Item("Galaxy S24" , 1300000 , 4);
        itemRepository.save(item2);

        itemRepository.update(item2.getId() , new Item("IPhone 15 Pro" , 2000000 , 5));
        // 진짜 리포지토리에서 값을 수정한거 알겠어 근데
        Item result = itemRepository.findById(item2.getId());
        System.out.println("item2 = " + item2);
        System.out.println("result = " + result);
        org.assertj.core.api.Assertions.assertThat(item2).isNotSameAs(result);

    }
}