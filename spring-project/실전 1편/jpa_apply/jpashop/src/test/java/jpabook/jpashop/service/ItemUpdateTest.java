package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.parser.Entity;

@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{


        //find 후 트랜잭션 안에선 이름 바꿔치기 함


        //변경 감지 == 더티체킹(dirty checking)
        // 후에 커밋
        // 즉 find 후 수정하면 => 트랜잭션에서 이름 바꿔치기 => 변경 감지 후 커밋
    }
}
