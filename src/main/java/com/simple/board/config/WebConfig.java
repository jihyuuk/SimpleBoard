package com.simple.board.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    // /image/** url 요청시 storage 폴더를 찾아가게함
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //storage 폴더 절대 경로 알아내기
        String path = Paths.get("storage/upload").toAbsolutePath().toString();
        //윈도우의 경우 c: (드라이버레터) 지워야함
        path = path.substring(path.indexOf(":")+1);

        registry.addResourceHandler("/image/**")
                .addResourceLocations("file://"+path+"/");
    }
}
