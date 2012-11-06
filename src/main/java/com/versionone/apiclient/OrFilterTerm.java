package com.versionone.apiclient;

/**
 * Combine terms in a filter using 'or'
 *
 * @author jerry
 */
public class OrFilterTerm extends GroupFilterTerm {

    /**
     * Create filter collection of terms
     *
     * @param terms
     */
    public OrFilterTerm(IFilterTerm... terms) {
        super(terms);
    }

    @Override
    String getTokenSeperator() {
        return "|";
    }
}
