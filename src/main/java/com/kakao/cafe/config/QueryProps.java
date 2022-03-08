package com.kakao.cafe.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class QueryProps {

    private static final String queryPath = "src/main/resources/sql/query.yml";

    private Map<String, String> props = new HashMap<>();

    public QueryProps() {
        try {
            File file = new File(queryPath);
            props = new Yaml().load(new FileReader(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return props.get(key);
    }

}
