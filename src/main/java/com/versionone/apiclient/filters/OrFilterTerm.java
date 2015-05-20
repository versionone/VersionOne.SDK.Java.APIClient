package com.versionone.apiclient.filters;


/**
 * Combine terms in a filter using 'or'
 */
public class OrFilterTerm extends GroupFilterTerm {

    /**
     * Create filter collection of terms
     *
     * @param terms - IFilterTerm
     */
    public OrFilterTerm(IFilterTerm... terms) {
        super(terms);
    }

    @Override
    String getTokenSeperator() {
        return "|";
    }
}
