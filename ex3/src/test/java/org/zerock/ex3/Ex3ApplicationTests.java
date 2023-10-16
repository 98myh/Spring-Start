package org.zerock.ex3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
class Ex3ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testConnection() {

		try (Connection con =
					 DriverManager.getConnection(
							 "jdbc:mariadb://localhost:3306/bootex?serverTimezone=Asia/Seoul",
							 "bootuser",
							 "1234")) {
			System.out.println(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
