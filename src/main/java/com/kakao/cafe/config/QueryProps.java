package com.kakao.cafe.config;

import com.kakao.cafe.repository.jdbc.Query;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class QueryProps {

    private Map<String, String> props = new HashMap<>();

    public QueryProps() {
        try {
            ClassPathResource resource = new ClassPathResource("query.yml");
            props = new Yaml().load(new FileReader(resource.getFile()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(Query query) {
        return props.get(query.name());
    }

}
