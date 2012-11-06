package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.List;

/**
 * Order a Query
 * @author jerry
 *
 */
public class OrderBy
{
	/**
	 * Supported sor orders
	 * @author jerry
	 *
	 */
	public enum Order
	{
		/**
		 * Low to High
		 */
		Ascending,
		/**
		 * High to Low
		 */
		Descending
	}

	List<Term> _terms = new ArrayList<Term>();

	public List<Term> getTerms() { return (List<Term>) java.util.Collections.unmodifiableCollection(_terms); }

	/**
	 * Set the attribute and order for major sort of returned data
	 * @param attribdef
	 * @param order
	 */
	public void majorSort(IAttributeDefinition attribdef, Order order)
	{
		remove(attribdef);
		_terms.add(0,new Term(attribdef, order));
	}

	/**
	 * Set the attribute and order for minor sort of returned data
	 * @param attribdef
	 * @param order
	 */
	public void minorSort(IAttributeDefinition attribdef, Order order)
	{
		remove(attribdef);
		_terms.add(new Term(attribdef, order));
	}

	/**
	 * Return the number of terms
	 * @return
	 */
	public int size() { return _terms.size(); }

	/**
	 * get the sort token
	 * @return
	 */
	public String getToken() {
		return TextBuilder.join(_terms,",");
	}

	/**
	 * Get the string representation of the sort
	 */
	@Override
	public String toString() { return getToken(); }

	private void remove(IAttributeDefinition attribdef)
	{
		int index = indexOf(attribdef);
		if (index == -1)
			return;
		_terms.remove(index);
	}

	private int indexOf(IAttributeDefinition attribdef)
	{
		for (int i=0;i<_terms.size();i++)
			if(((OrderBy.Term)_terms.get(i)).getAttributeDefinition() == attribdef)
				return i;
		return -1;
	}


	class Term
	{
		private IAttributeDefinition AttributeDefinition;
		private OrderBy.Order Order;

		public IAttributeDefinition getAttributeDefinition() {return AttributeDefinition;}
		public OrderBy.Order getOrder() {return Order;}

		public Term(IAttributeDefinition attribdef, Order order)
		{
			AttributeDefinition = attribdef;
			Order = order;
		}

		public String getToken() {
			StringBuffer token = new StringBuffer();
			if(Order != OrderBy.Order.Ascending)
				token.append("-");
			token.append(AttributeDefinition.getToken());
			return token.toString();
		}

		@Override
		public String toString() { return getToken(); }
	}
}
