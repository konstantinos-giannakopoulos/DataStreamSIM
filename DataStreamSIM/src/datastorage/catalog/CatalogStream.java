package datastorage.catalog;

import java.util.ArrayList;

/**
 * It contains all the stored information for a stream.
 * 
 * @version 1.0
 * @author kostas
 */
public class CatalogStream {
	
	/* The name of the stream. */
	private String streamName;
	
	/* Attributes' types and values. */
	private ArrayList<String[]> attributes;
	
	private ArrayList<Integer> SteMsToBeStored;
	private ArrayList<Integer> SteMsToProbe;
	private ArrayList<Integer> PredicatesToBeScheduled;
	
	/**
	 * Constructor of class <code>CatalogStream</code>
	 */
	public CatalogStream() {
		this.streamName = null;
		this.attributes = new ArrayList<String[]>();
		this.SteMsToBeStored = new ArrayList<Integer>();
		this.SteMsToProbe = new ArrayList<Integer>();
		this.PredicatesToBeScheduled = new ArrayList<Integer>();
	}//end constructor CatalogStream()
	
	public void setStreamName(String strname) {
		this.streamName = strname;
	}
	
	public String getStreamName() {
		return this.streamName;
	}
	
	/**
	 * Fill the attributes info, types and values, for this stream.
	 * @param strTypeValue (type,value) pairs that correspond 
	 * to each attribute of the stream.
	 */
	public void setAttibutes(String[] strTypeValue) {
		int i;
		for(i = 1; i < strTypeValue.length; i++) {
			//split the type from the value
			String[] s = strTypeValue[i].split(":");
			String[] tmp = new String[2];
			tmp[0] = s[0]; //type
			tmp[1] = s[1]; //value
			this.attributes.add(tmp);
		}
	}//end method setAttibutes()
	
	public ArrayList<String[]> getAttributes() {
		return this.attributes;
	}
	
	public ArrayList<Integer> getSteMsToBeStored() {
		return SteMsToBeStored;
	}

	public void setSteMsToBeStored(ArrayList<Integer> steMsToBeScheduled) {
		SteMsToBeStored = steMsToBeScheduled;
	}
	
	public ArrayList<Integer> getSteMsToProbe() {
		return SteMsToProbe;
	}

	public void setSteMsToProbe(ArrayList<Integer> steMsToProbe) {
		SteMsToProbe = steMsToProbe;
	}

	public ArrayList<Integer> getPredicatesToBeScheduled() {
		return PredicatesToBeScheduled;
	}

	public void setPredicatesToBeScheduled(ArrayList<Integer> predicatesToBeScheduled) {
		PredicatesToBeScheduled = predicatesToBeScheduled;
	}

	public String getAttributeAtIndex(int index) {
		return this.attributes.get(index)[1];
	}
	
	public String getAttributeTypeAtIndex(int index) {
		return this.attributes.get(index)[0];
	}
	
	public void addToSteMsToBeStored(int stem) {
		getSteMsToBeStored().add(stem);
	}//end method addToSteMsToBeStored()
	
	public void addToSteMsToProbe(int stem) {
		getSteMsToProbe().add(stem);
	}//end method addToSteMsToProbe()
	
	public void addToPredicatesToBeScheduled(int predicate) {
		getPredicatesToBeScheduled().add(predicate);
	}//end method addToPredicatesToBeScheduled()
	
	/**
	 * Find the number of the attributes this stream
	 * contains
	 */
	public int findNoAttribute() {
		int no = getAttributes().size();
		return no;
	}//end method findNoAttribute()
	
	/**
	 * Find the index position of an attribute in the stream.
	 * 
	 * @param attributename the name of the attribute.
	 * @return The index position.
	 */
	public int findIndexOfAttribute(String attributename) {
		int i;
		for(i=0; i < getAttributes().size(); i++) {
			if(attributename.equals(getAttributes().get(i)[1])) {
				return i;
			}
		}
		System.out.println("CatalogStream.java: Attribute not found in catalog!");
		return -1;
	}//end method getIndexOfAttribute()
	
	
	public void create(String[] fields) {
		setStreamName(fields[0].toString());
		setAttibutes(fields);
	}//end method create()

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "CatalogStream [streamName=" + streamName + ", attributes="
				+ attributes + ", SteMsToBeStored=" + SteMsToBeStored
				+ ", SteMsToProbe=" + SteMsToProbe
				+ ", PredicatesToBeScheduled=" + PredicatesToBeScheduled + "]";
	}
	
}//end class CatalogStream
