package com.ljqweb.community_rap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ljqweb.community_rap.mapper")
public class CommunityRapApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityRapApplication.class, args);
    }

}
