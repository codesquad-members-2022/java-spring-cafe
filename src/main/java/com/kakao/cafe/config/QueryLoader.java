package com.kakao.cafe.config;

import com.kakao.cafe.repository.Query;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueryLoader {

    private Map<String, String> loader = new HashMap<>();

    public QueryLoader() {
        try {
            ClassPathResource resource = new ClassPathResource("query.yml");
            loader = new Yaml().load(new InputStreamReader(resource.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(Query query) {
        return loader.get(query.name());
    }
}
