package com.bej;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@OpenAPIDefinition(info=@Info(title = "KanbanBoard-API", version = "2.0", description = "This is KanbanBoardService Application"))
@SpringBootTest
class KanbanBoardServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
