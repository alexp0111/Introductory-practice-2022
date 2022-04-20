package com.example.project;

import androidx.annotation.NonNull;

public class Champion implements Comparable<Object> {
    private String id;
    private Integer score;
    private String nick;

    public Champion(Integer score, String id, String nick) {
        this.score = score;
        this.id = id;
        this.nick = nick;
    }

    @Override
    public int compareTo(Object o) {
        Champion f = (Champion) o;
        if (this.score.equals(f.score)) {
            return -1;
        } else {
            return this.score - f.score;
        }
    }


    @NonNull
    public Integer getScore() {
        return this.score;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    @NonNull
    public String getNick() {
        return this.nick;
    }
}
