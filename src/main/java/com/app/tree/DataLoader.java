package com.app.tree;

import com.app.tree.model.Node;
import com.app.tree.repository.NodeRepo;
import com.google.gson.Gson;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.json.*;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final NodeRepo nodeRepo;

    public DataLoader(NodeRepo nodeRepo) {
        this.nodeRepo = nodeRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        File resource = new ClassPathResource("db_init.json").getFile();
        String nodes_str = new String(Files.readAllBytes(resource.toPath()));

        JSONObject nodes_json = new JSONObject(nodes_str);
        JSONArray nodes = nodes_json.getJSONArray("nodes");

        List<Node> parsed_nodes = new LinkedList<>();
        Gson g = new Gson();
        for (Object node : nodes) {
            parsed_nodes.add(g.fromJson(node.toString(), Node.class));
        }

        nodeRepo.saveAll(parsed_nodes);
    }
}
