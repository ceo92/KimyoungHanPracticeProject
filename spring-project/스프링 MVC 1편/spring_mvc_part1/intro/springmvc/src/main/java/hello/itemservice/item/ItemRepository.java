package hello.itemservice.item;


import java.util.List;

public interface ItemRepository {

    Item save(Item item); //테스트하기 위해 잘 저장됐는지 Item 반환
    Item findById(Long id); //아 Long으로 한 이유가 id값은필수로 할당해야하기 때문에 기초타입이 아닌 포장클래스로 했구나
    List<Item> findAll();
    void update(Long itemId , Item updateParam); //업데이트 로직은 1.아이디에 해당되는 객체 반환 2. 업데이트 !그냥 업데이트 하면 안되니
    // 서버가 정한 로직을 update를 통하여 클라이언트가 저장하고 삭제하고 수정하



}
