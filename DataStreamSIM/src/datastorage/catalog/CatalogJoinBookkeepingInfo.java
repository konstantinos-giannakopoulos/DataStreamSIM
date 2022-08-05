package datastorage.catalog;

import java.util.ArrayList;

/**
 * Bookkeeping info for the joins of the 
 * execution plan.
 * 
 * @version 1.0
 * @author kostas
 */
public class CatalogJoinBookkeepingInfo {
	
	/* The name of the stream. */
	private String stream;
	
	/* The no of the attributes of this stream. */
	private int noAttributes;
	
	private String attributeName;
	
	private int attributePos;
	
	private ArrayList<String> joinStreams;
	
	/**
	 * Constructor of class <code>CatalogJoinBookkeepingInfo</code>
	 */
	public CatalogJoinBookkeepingInfo() {
		this.stream = new String();
		this.noAttributes = 0;
		this.attributeName = new String();
		this.joinStreams = new ArrayList<String>();
	}//end constructor CatalogJoinBookkeepingInfo()

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public int getNoAttributes() {
		return noAttributes;
	}

	public void setNoAttributes(int noAttributes) {
		this.noAttributes = noAttributes;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public int getAttributePos() {
		return attributePos;
	}

	public void setAttributePos(int attributePos) {
		this.attributePos = attributePos;
	}

	public ArrayList<String> getJoinStreams() {
		return joinStreams;
	}

	public void setJoinStreams(ArrayList<String> joinStreams) {
		this.joinStreams = joinStreams;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "CatalogJoinBookkeepingInfo [stream=" + stream
				+ ", noAttributes=" + noAttributes + ", attributeName="
				+ attributeName + ", attributePos=" + attributePos
				+ ", joinStreams=" + joinStreams + "]";
	}
	
}//end class CatalogJoinBookkeepingInfo
