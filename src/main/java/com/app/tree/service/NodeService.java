package com.app.tree.service;

import com.app.tree.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.tree.repository.NodeRepo;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NodeService {
    private final NodeRepo nodeRepo;

    @Autowired
    public NodeService(NodeRepo nodeRepo) {
        this.nodeRepo = nodeRepo;
    }

    public Node addNode(Node node) {
        return this.nodeRepo.save(node);
    }

    public List<Node> getAllNodes() {
        return this.nodeRepo.findAll();
    }

    public Node updateNode(Node node) {
        this.nodeRepo.deleteById(node.getId());
        return this.nodeRepo.save(node);
    }

    public void deleteNodeById(Integer id) {
        Optional<Node> currNode = this.nodeRepo.findById(id);

        if (currNode.isPresent()) {
            this.nodeRepo.deleteById(id);

            List<Node> nodes = this.getAllNodes();
            for (Node node : nodes) {
                if (node.getParent() == currNode.get().getId())
                    deleteNodeById(node.getId());
            }
        }
    }

    public void makeLeaf(Integer id) {
        this.nodeRepo.makeLeaf(id);
    }

    public void makeIntermediate(Integer id) {
        Optional<Node> currNode = this.nodeRepo.findById(id);

        if (currNode.isPresent()) {
            List<Node> nodes = this.getAllNodes();
            for (Node node : nodes) {
                if (node.getParent() == currNode.get().getId())
                    return;
            }
        }

        this.nodeRepo.makeIntermediate(id);
    }
}
