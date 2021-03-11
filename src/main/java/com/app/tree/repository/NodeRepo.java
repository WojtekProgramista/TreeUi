package com.app.tree.repository;

import com.app.tree.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NodeRepo extends JpaRepository<Node, Integer> {
    @Modifying
    @Query("update Node n set n.type = 'leaf' where n.id = :nodeId and n.type not like 'root'")
    void makeLeaf(@Param("nodeId") Integer nodeId);

    @Modifying
    @Query("update Node n set n.type = 'intermediate' where n.id = :nodeId and n.type not like 'root'")
    void makeIntermediate(@Param("nodeId") Integer nodeId);

}
