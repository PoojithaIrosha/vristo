package com.poojithairosha.vristopos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VristoPosApplication {

    private static final Logger log = LoggerFactory.getLogger(VristoPosApplication.class);

//    @Autowired
//    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(VristoPosApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder) {
//        return args -> {
//            log.info("User registered");
//            userService.registerUser(User.builder().name("Poojitha Irosha").email("poojitha@gmail.com").mobile("0762873649").role(UserRole.ROLE_USER).password(passwordEncoder.encode("123")).username("poojitha").isEnabled(true).build());
//        };
//    }

}
