package com.versionone.apiclient.fitnesse.metamodel;

import com.versionone.utils.Version;

/**
 * Check the version
 */
public class TestVersion extends fit.RowFixture
{
	@SuppressWarnings("unchecked")
	@Override
	public Class getTargetClass() {
		return VersionEntry.class;
	}

	@Override
	public Object[] query() throws Exception {
		Object[] rc = new Object[1];
		rc[0] = new VersionEntry(Setup.model.getVersion());
		return rc;
	}
	
	public static class VersionEntry {
		public int major;
		public int minor;
		public int build;
		public int revision;
		
		VersionEntry(Version value) {
			major = value.getMajor();
			minor = value.getMinor();
			build = value.getBuild();
			revision = value.getRevision();
		}
	}
}
