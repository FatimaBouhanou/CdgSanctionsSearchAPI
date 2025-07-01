package com.example.demo.model;


public class ScoredEntity<T> {
    private T entity;
    private double score;

    public ScoredEntity(T entity, double score) {
        this.entity = entity;
        this.score = score;
    }

    public T getEntity() {
        return entity;
    }

    public double getScore() {
        return score;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

