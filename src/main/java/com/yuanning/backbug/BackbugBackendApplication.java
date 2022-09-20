package com.yuanning.backbug;

import com.yuanning.backbug.entity.Role;
import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.UserRole;
import com.yuanning.backbug.entity.messageEnum.RoleType;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.RoleRepository;
import com.yuanning.backbug.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class BackbugBackendApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackbugBackendApplication.class, args);
	}

	@Override
	public void run(java.lang.String... args) {
		//初始化角色信息
		for (RoleType roleType : RoleType.values()) {
			roleRepository.save(new Role(roleType.getName(), roleType.getDescription()));
		}
		// 初始化一个 admin 用户
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setEnabled(true);
		user.setPassword(bCryptPasswordEncoder.encode("root"));
		user.setEmail("admin@123.com");
		userRepository.save(user);
		Role role = roleRepository.findByName(RoleType.ADMIN.getName()).get();
		userRoleRepository.save(new UserRole(user, role));
		//
	}

}
