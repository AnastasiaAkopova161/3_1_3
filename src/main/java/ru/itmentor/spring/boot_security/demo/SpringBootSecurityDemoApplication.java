package ru.itmentor.spring.boot_security.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
@EnableJpaRepositories("ru.itmentor.spring.boot_security.demo.repository")
@EntityScan("ru.itmentor.spring.boot_security.demo.model")
public class SpringBootSecurityDemoApplication {

	private final RoleService roleService;
	private final UserService userService;

	@Autowired
	public SpringBootSecurityDemoApplication(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@PostConstruct
	public void createRolesAndUsers() {
        /*
         Для начального функционала приложения необходимо создать роли и пользователей
         роли - ROLE_ADMIN и ROLE_USER
         Для роли "ROLE_ADMIN" должен быть создан пользователь "admin"
         Дальнейшее добавление пользователей с наделением ролями, будет производиться из админки
        */

		// Создание ролей
		// Админ
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleService.createRole(adminRole);
		// Пользователь
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleService.createRole(userRole);

		// Создание пользователей
		// Админ
		User user1 = new User();
		user1.setFirstName("Denis");
		user1.setLastName("Odeckov");
		user1.setEmail("od123@com");
		user1.setUsername("admin");
		user1.setPassword("123456");
		// Добавление роли
		Set<Role> user1Roles = new HashSet<>();
		user1Roles.add(adminRole);
		user1.setRoles(user1Roles);
		user1.addRole(adminRole); // Add this line to add the role to the user
		userService.createUser(user1);
	}
}

