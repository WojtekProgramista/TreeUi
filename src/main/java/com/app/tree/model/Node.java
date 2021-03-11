package com.app.tree.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Node")
public class Node implements Serializable{
    @Id
    private Integer id;
    @Nullable
    private Integer value;
    @Nullable
    private Integer parent;
    private String color;
    private String type;

    public Node() {}

    public Node(Integer id, Integer value, Integer parent, String color, String type) {
        this.id = id;
        this.value = value;
        this.parent = parent;
        this.color = color;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", value=" + value +
                ", parent=" + parent +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}