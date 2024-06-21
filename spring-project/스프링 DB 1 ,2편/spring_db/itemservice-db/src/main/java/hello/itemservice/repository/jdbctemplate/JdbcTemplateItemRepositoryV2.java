package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * NamedParameterJdbcTemplate
 * SqlParameterSource
 * - BeanPropertySqlParameterSource
 * - MapSqlParameterSource
 * Map
 *
 * BeanPropertyRowMapper
 *
 */
@Slf4j
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

    //private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name , price , quantity) " +
                "values (:itemName, :price , :quantity)";

        //item 객체의 필드이름을 통해 파라메터 생성 , 그러면 파라메터에 일대일 대응 되겠지
        //이렇게 파라메터 바인딩 성공 ! 각 이름에 바인딩되게됨
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        //키 홀더를 쓰면 이렇게 복잡해짐 걍 이렇게 쓰는구나 정도 이해


        long key = keyHolder.getKey().longValue();
        item.setId(key);

        return item;

    }


    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        //id에 일치하는 상품명 가격 수량을 변경
        String sql = "update item set item_name = :itemName price = :price quantity =:quantity where id=:id";
        //자바에선 itemName으로 설정되어있고 db에선 item_name으로 설정되어있으니 :itemName을 해줘야됨

        //update 쿼리도 이름 기반 파라메터 당연히 바인딩할 때 넘겨야겠지
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);
        template.update(sql , param);

    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select * from item where id=:id";
        try {
            Map<String, Object> param = Map.of("id", id); //key도 id , value도 id
            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    private RowMapper<Item> itemRowMapper() {
        //Item의 각각에 이름에 알아서 매핑해줌
        return BeanPropertyRowMapper.newInstance(Item.class);
    }




    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        //검색 조건(itemName , maxPrice)을 갖고 파라메터 데이터를 넣어두는 것
        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select * from item"; //동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag = true;
        }
        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }
        log.info("sql={}", sql);
        return template.query(sql,param , itemRowMapper());
    }


}
