package com.duelistic;

/**
 * Rank class that syncs up with an elo range
 */
public class Rank {
    private final String name;
    private final int minElo, maxElo;
    public Rank(String name, int minElo, int maxElo) {
        this.name = name;
        this.minElo = minElo;
        this.maxElo = maxElo;
    }

    /**
     * @return name of the rank
     */
    public String getName() {
        return name;
    }

    /**
     * @return minimum elo that's required for the rank
     */
    public int getMinElo() {
        return minElo;
    }

    /**
     * @return maximum elo for the rank
     */
    public int getMaxElo() {
        return maxElo;
    }
}
