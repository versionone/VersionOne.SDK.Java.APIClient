package com.versionone.apiclient;

/**
 * Combine terms on a filter using 'and'
 *
 * @author jerry
 */
public class AndFilterTerm extends GroupFilterTerm {
    /**
     * Create filter with a collection of terms
     *
     * @param terms - IFilterTerm data
     */
    public AndFilterTerm(IFilterTerm... terms) {
        super(terms);
    }

    @Override
    String getTokenSeperator() {
        return ";";
    }
}
