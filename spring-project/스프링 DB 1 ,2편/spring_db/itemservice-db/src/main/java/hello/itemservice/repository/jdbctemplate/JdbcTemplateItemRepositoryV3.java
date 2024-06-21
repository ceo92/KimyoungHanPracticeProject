package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * SimpleJdbcInsert
 */
@Slf4j
public class JdbcTemplateItemRepositoryV3 implements ItemRepository {

    //private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;
    public JdbcTemplateItemRepositoryV3(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("item") //테이블명만 알면 해당테이블에 어떤 칼럼이 있는지 알수있음
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Item save(Item item) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(item);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        item.setId(key.longValue());
        return item; //저장 시에는 저장 잘 되었음을 알려주기 위해 저장후 시스템으로부터 만들어진 키를 여기에 반환받아서 객체 만든 후 응답
        // SimpleJdbcInsert =>
    }


    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        //id에 일치하는 상품명 가격 수량을 변경
        String sql = "update item set item_name = :itemName , price = :price , quantity =:quantity where id=:id";
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
        //파라메터 가져옴 => 빈 프로퍼티 접근법을 통해 파라메터에 바인딩

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
