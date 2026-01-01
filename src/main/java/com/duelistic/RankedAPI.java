package com.duelistic;

import java.util.*;

/**
 * RankedAPI used to handle a both elo- and rank-name- system
 */
public class RankedAPI {
    private NavigableSet<Rank> ranks;

    /**
     * Constructs the api
     * @param ranks
     */
    public RankedAPI(Rank... ranks) {
        this.ranks = new TreeSet<>(Comparator.comparingInt(Rank::getMinElo));
        for (Rank rank : ranks) {
            validateNoOverlap(rank);
            this.ranks.add(rank);
        }
    }

    /**
     * Loops through the ranks set to find a rank in the specified elo
     * @param elo
     * @return Rank of the set ranks that includes the parametric elo
     */
    public Rank getRankForElo(int elo) {
        Rank rank = ranks.floor(probeForElo(elo));
        if (rank != null && (rank.getMaxElo() == -1 || elo <= rank.getMaxElo())) {
            return rank;
        }
        return null;
    }

    private Rank probeForElo(int elo) {
        return new Rank(null, elo, elo);
    }

    /**
     * Returns the specified ranks
     * @return unmodifiable ranks
     */
    public Set<Rank> getRanks() {
        return Collections.unmodifiableSet(ranks);
    }

    private void validateNoOverlap(Rank rank) {
        int min = rank.getMinElo();
        int max = rank.getMaxElo();
        if (max != -1 && min > max) {
            throw new IllegalArgumentException("minElo > maxElo");
        }
        Rank lower = ranks.floor(rank);
        Rank higher = ranks.ceiling(rank);
        if (lower != null) {
            int lowerMax = lower.getMaxElo();
            if (lowerMax == -1) {
                throw new IllegalArgumentException("Rank after open-ended rank");
            }
            if (lowerMax >= min) {
                throw new IllegalArgumentException("Overlapping rank ranges");
            }
        }
        if (higher != null) {
            if (max == -1) {
                throw new IllegalArgumentException("Open-ended rank must be last");
            }
            if (max >= higher.getMinElo()) {
                throw new IllegalArgumentException("Overlapping rank ranges");
            }
        }
    }

}