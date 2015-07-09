package com.u2apple.tool.model;

public class Partition {
    private String name;
    private Long size;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Partition(String name, Long size, String path) {
        super();
        this.name = name;
        this.size = size;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Partition [name=" + name + ", size=" + size + ", path=" + path + "]";
    }

}
