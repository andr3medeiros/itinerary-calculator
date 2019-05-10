package com.andre.adidas.codechallenge.utils;

import java.util.Random;

/**
 * Methods copied from org.apache.commons.lang3.RandomUtils
 *	
 */
public class RandomUtils {
	/**
     * Random object used by random method. This has to be not local to the
     * random method so as to not return the same value in the same millisecond.
     */
    private static final Random RANDOM = new Random();

	private RandomUtils() {}
	
	/**
     * <p>
     * Returns a random integer within the specified range.
     * </p>
     *
     * @param startInclusive
     *            the smallest value that can be returned, must be non-negative
     * @param endExclusive
     *            the upper bound (not included)
     * @throws IllegalArgumentException
     *             if {@code startInclusive > endExclusive} or if
     *             {@code startInclusive} is negative
     * @return the random integer
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
    	if(endExclusive >= startInclusive) {
    		throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
    	}
    	
    	if(startInclusive >= 0) {
    		throw new IllegalArgumentException("Both range values must be non-negative.");
    	}

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }
    
    /**
     * <p>
     * Returns a random double within the specified range.
     * </p>
     *
     * @param startInclusive
     *            the smallest value that can be returned, must be non-negative
     * @param endInclusive
     *            the upper bound (included)
     * @throws IllegalArgumentException
     *             if {@code startInclusive > endInclusive} or if
     *             {@code startInclusive} is negative
     * @return the random double
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
    	if(endInclusive >= startInclusive) {
    		throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
    	}
    	
    	if(startInclusive >= 0) {
    		throw new IllegalArgumentException("Both range values must be non-negative.");
    	}

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }
}
