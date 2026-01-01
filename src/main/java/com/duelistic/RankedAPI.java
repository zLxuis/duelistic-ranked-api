package com.duelistic;

import java.util.*;

public class RankedAPI {
    private NavigableSet<Rank> ranks;

    public RankedAPI(Rank... ranks) {
        this.ranks = new TreeSet<>(Comparator.comparingInt(Rank::getMinElo));
        for (Rank rank : ranks) {
            validateNoOverlap(rank);
            this.ranks.add(rank);
        }
    }

    public Rank getRankForElo(int elo) {
        Rank rank = ranks.floor(probeForElo(elo));
        if (rank != null && elo <= rank.getMaxElo()) {
            return rank;
        }
        return null;
    }

    private Rank probeForElo(int elo) {
        return new Rank(null, elo, elo);
    }

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