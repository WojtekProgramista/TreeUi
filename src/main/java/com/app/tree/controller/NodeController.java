package com.app.tree.controller;

import com.app.tree.model.Node;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.tree.service.NodeService;

import java.util.List;

@RestController
@RequestMapping("/node")
public class NodeController {
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Node> addNode(@RequestBody Node node) {
        Node newNode = this.nodeService.addNode(node);
        return new ResponseEntity<>(newNode, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Node> updateNode(@RequestBody Node node) {
        Node newNode = this.nodeService.updateNode(node);
        return new ResponseEntity<>(newNode, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Node>> getAllNodes() {
        List<Node> allNodes = this.nodeService.getAllNodes();
        return new ResponseEntity<>(allNodes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable("id") Integer id) {
        this.nodeService.deleteNodeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/leaf/{id}")
    public ResponseEntity<Void> makeLeaf(@PathVariable("id") Integer id) {
        this.nodeService.makeLeaf(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/intermediate/{id}")
    public ResponseEntity<Void> makeIntermediate(@PathVariable("id") Integer id) {
        this.nodeService.makeIntermediate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
