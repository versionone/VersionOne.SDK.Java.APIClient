package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.versionone.Oid;
import com.versionone.apiclient.IAttributeDefinition.AttributeType;

/**
 * Represents a VersionOne API Query
 *
 * @author Jerry D. Odenwelder Jr.
 */
public class Query {
    public static final Date MIN_DATE = new Date(0);

    private IAssetType assetType = null;
    private AttributeSelection selection = new AttributeSelection();
    private boolean isHistorical;
    private Oid oid;
    private IAttributeDefinition parentRelation;
    private IFilterTerm filter = null;
    private OrderBy orderBy = new OrderBy();
    private Paging paging = new Paging();
    private Date asOf = MIN_DATE;
    private QueryFind find = null;
    private List<QueryVariable> variables = new ArrayList<QueryVariable>();

    /**
     * Create a query on an Asset Type
     *
     * @param assettype - type of asset you wish to query
     */
    public Query(IAssetType assettype) {
        this(assettype, false);
    }

    /**
     * Create a query on attribute in an Asset
     *
     * @param assettype      - type of asset to query
     * @param parentrelation - attribtue to query
     */
    public Query(IAssetType assettype, IAttributeDefinition parentrelation) {
        this(assettype, false, parentrelation);
    }

    /**
     * Create a historical query on an asset
     *
     * @param assettype  - type of asset to query
     * @param historical - indicate if you want a history query
     */
    public Query(IAssetType assettype, boolean historical) {
        this(assettype, historical, null);
    }

    /**
     * Create a historical query on an attribute
     *
     * @param assettype      - type of asset to query
     * @param historical     - indicate if you want a history query
     * @param parentrelation - attribtue to query
     */
    public Query(IAssetType assettype, boolean historical, IAttributeDefinition parentrelation) {
        assetType = assettype;
        isHistorical = historical;
        oid = Oid.Null;
        parentRelation = parentrelation;
        if (parentRelation != null) {
            if (parentRelation.getAttributeType() != AttributeType.Relation)
                throw new RuntimeException("Parent Relation must be a Relation Attribute Type");
            if (parentRelation.isMultiValue())
                throw new RuntimeException("Parent Relation cannot be multi-value");
        }
    }

    /**
     * Create a Query from an OID
     *
     * @param oid        - oid to build query
     * @param historical - do you want a historical query
     */
    public Query(Oid oid, boolean historical) {
        if (oid.isNull())
            throw new RuntimeException("Invalid Query OID Parameter");
        else if (oid.hasMoment() && historical)
            throw new UnsupportedOperationException("Historical Query with Momented OID not supported");

        isHistorical = historical;
        assetType = oid.getAssetType();
        this.oid = oid;
    }

    /**
     * Create a data query from an IOD
     *
     * @param oid - oid to build query
     */
    public Query(Oid oid) {
        this(oid, false);
    }

    /**
     * Is this a historical query
     *
     * @return true if this query is historical
     */
    public boolean isHistorical() {
        return isHistorical;
    }

    /**
     * get the asset type for the query
     *
     * @return IAssetType used for query
     */
    public IAssetType getAssetType() {
        return assetType;
    }

    /**
     * Get the query oid
     *
     * @return Object Identifier used in query
     */
    public Oid getOid() {
        return oid;
    }

    /**
     * The the AttributeDefinition
     *
     * @return IAttributedefinition used in query
     */
    IAttributeDefinition getParentRelation() {
        return parentRelation;
    }

    /**
     * Get the attributes used in a 'sel'
     *
     * @return AttributeSelection for this query
     */
    public AttributeSelection getSelection() {
        return selection;
    }

    /**
     * Set the attribute selection
     *
     * @param value
     */
    public void setSelection(AttributeSelection value) {
        if (null != value)
            selection = value;
    }

    /**
     * Set a filter
     *
     * @param value
     */
    public void setFilter(IFilterTerm value) {
        if (null != value) {
            filter = value;
        }
    }

    /**
     * Get the filter
     *
     * @return IFilterTerm for this query
     */
    public IFilterTerm getFilter() {
        return filter;
    }

    /**
     * Set OrderBy information
     *
     * @param value
     */
    public void setOrderBy(OrderBy value) {
        if (null != value)
            orderBy = value;
    }

    /**
     * Get the OrderBy information
     *
     * @return OrderBy terms for this query
     */
    public OrderBy getOrderBy() {
        return orderBy;
    }

    /**
     * Set paging details
     *
     * @param value
     */
    public void setPaging(Paging value) {
        if (null != value)
            paging = value;
    }

    /**
     * Get Paging details
     *
     * @return Paging for this query
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     * Set asOf Date
     *
     * @param value
     */
    public void setAsOf(Date value) {
        asOf = value;
    }

    /**
     * Get asOf Date
     *
     * @return AsOf Date for this query
     */
    public Date getAsOf() {
        return asOf;
    }

    /**
     * @return query find parameter
     */
    public QueryFind getFind() {
        return find;
    }

    /**
     * @param value query find parameter
     */
    public void setFind(QueryFind value) {
        find = value;
    }

    public List<QueryVariable> getVariables() {
        return variables;
    }
}
