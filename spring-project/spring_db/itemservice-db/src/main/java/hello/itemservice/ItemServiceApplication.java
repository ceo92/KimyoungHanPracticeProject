package hello.itemservice;

import hello.itemservice.config.*;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
//@Import(JdbcTemplateV3Config.class)
//@Import(MyBatisConfig.class)
//@Import(QuerydslConfig.class)
//@Import(SpringDataJpaConfig.class)
@Import(V2Config.class)
@Slf4j
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}
	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

/*
	@Bean
	@Profile("test") //test 프로필인 경우 해당 데이터 소스가 빈으로 등록!
	//즉 테스트할 경우 DataSource를 빈으로 직ㅈ버 등록할 거싱ㅁ!
	public DataSource dataSource() {
		log.info("메모리 데이터베이스 초기화");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//H2 데이터베이스 이름을 지정해줌 ! H2 데이터베이스 구현체(드라이버)를 쓰겠다 ㅇjdbc
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=1");
		//jvm(메모리) 내에 데이터베이스 만들고 거기다가 데이터 쌓음
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}*/
}
