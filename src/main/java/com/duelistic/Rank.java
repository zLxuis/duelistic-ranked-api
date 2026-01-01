package com.duelistic;

public class Rank {
    private final String name;
    private final int minElo, maxElo;
    public Rank(String name, int minElo, int maxElo) {
        this.name = name;
        this.minElo = minElo;
        this.maxElo = maxElo;
    }
    
    public String getName() {
        return name;
    }

    public int getMinElo() {
        return minElo;
    }

    public int getMaxElo() {
        return maxElo;
    }
}
