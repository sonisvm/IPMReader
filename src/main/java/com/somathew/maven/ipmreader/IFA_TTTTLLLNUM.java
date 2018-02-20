package com.somathew.maven.ipmreader;

import org.jpos.iso.IFA_LLLNUM;
import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.TaggedFieldPackagerBase;

public class IFA_TTTTLLLNUM extends TaggedFieldPackagerBase {

	@Override
	protected ISOFieldPackager getDelegate(int len, String description) {
		return new IFA_LLLNUM(len, description);
	}

	@Override
	protected int getTagNameLength() {
		return 4;
	}
	
}
