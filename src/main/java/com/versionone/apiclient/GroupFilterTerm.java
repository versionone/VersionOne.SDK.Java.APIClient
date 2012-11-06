package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for grouping filter terms (i.e and, or)
 *
 * @author jerry
 */
public abstract class GroupFilterTerm implements IFilterTerm {
    List<IFilterTerm> terms = new ArrayList<IFilterTerm>();

    /**
     * @return Has this grouping terms or not
     */
    public boolean hasTerm() {
        return terms.size() > 0;
    }

    public String getToken() throws APIException {
        return makeToken(true);
    }

    public String getShortToken() throws APIException {
        return makeToken(false);
    }

    private String makeToken(Boolean full) throws APIException {
        ArrayList<String> tokens = new ArrayList<String>();

        for (IFilterTerm term : terms) {
            String token = full ? term.getToken() : term.getShortToken();

            if (token != null) {
                tokens.add(token);
            }
        }
        
        if (tokens.isEmpty()) {
            return null;
        }
        
        if (tokens.size() == 1) {
            return tokens.get(0);
        }

        return "(" + TextBuilder.join(tokens.toArray(), getTokenSeperator()) + ")";
    }

    abstract String getTokenSeperator();

    protected GroupFilterTerm(IFilterTerm[] terms) {
        for (IFilterTerm term : terms) {
            if (term != null) {
                this.terms.add(term);
            }
        }
    }

    /**
     * Create an AndFilterTerm from the array of terms
     *
     * @param terms
     * @return
     */
    public GroupFilterTerm and(IFilterTerm... terms) {
        AndFilterTerm term = new AndFilterTerm(terms);
        this.terms.add(term);
        return term;
    }

    /**
     * create an OrFilterTerm from the array of terms
     *
     * @param terms
     * @return
     */
    public GroupFilterTerm or(IFilterTerm... terms) {
        OrFilterTerm term = new OrFilterTerm(terms);
        this.terms.add(term);
        return term;
    }

    /**
     * Create a filter term from the attribute definition
     *
     * @param def
     * @return
     */
    public FilterTerm Term(IAttributeDefinition def) {
        FilterTerm term = new FilterTerm(def);
        terms.add(term);
        return term;
    }
}
