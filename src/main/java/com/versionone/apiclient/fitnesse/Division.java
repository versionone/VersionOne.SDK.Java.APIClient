package com.versionone.apiclient.fitnesse;

import fit.ColumnFixture;

public class Division extends ColumnFixture
{
	public double numerator = 0.0;
	public double denominator = 0.0;

	public double quotient()
	{
		return numerator/denominator;
	}
}
