package com.kalki.blog;

import com.kalki.blog.entities.Role;
import com.kalki.blog.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// You can use this to test password encoding if you like
		System.out.println("Encoded password for 'xyz': " + passwordEncoder.encode("xyz"));

		try {
			Role role1 = new Role();
			role1.setId(501);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(502);
			role2.setName("ROLE_USER");

			List<Role> roles= List.of(role1,role2);

			List<Role> result=this.roleRepo.saveAll(roles);

			result.forEach(r ->{
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		// You can use this to test password encoding if you like
		System.out.println("Encoded password for 'xyz': " + passwordEncoder.encode("xyz"));
	}
}
