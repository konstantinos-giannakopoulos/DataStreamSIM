package datastorage.catalog;

import java.util.ArrayList;

import operators.Join;

/**
 * The implementation of the system catalog.
 * 
 * @author kostas
 * @version 1.0
 */
public class Catalog {
	
	/* Info about the attributes of each stream and their datatypes. */
	private ArrayList<CatalogStream> catalogStreams;
	
	/* Info about the join operators of the execution plan */
	private ArrayList<CatalogJoinBookkeepingInfo> catalogJoinBkkInfoList;
	
	private ArrayList<Join> joinList;
	
	/**
	 * Constructor of class <code>Catalog</code>
	 */
	public Catalog() {
		this.catalogStreams = new ArrayList<CatalogStream>();
		this.catalogJoinBkkInfoList = new ArrayList<CatalogJoinBookkeepingInfo>();
		this.joinList = new ArrayList<Join>();
	}//end constructor Catalog()
	
	public ArrayList<CatalogStream> getCatalogStreams() {
		return catalogStreams;
	}
	
	public void setCatalogStreams(ArrayList<CatalogStream> catalogStreams) {
		this.catalogStreams = catalogStreams;
	}

	public void addCatStream(CatalogStream s) {
		this.catalogStreams.add(s);
	}

	public ArrayList<CatalogJoinBookkeepingInfo> getCatalogJoinBkkInfoList() {
		return catalogJoinBkkInfoList;
	}

	public void setCatalogJoinBkkInfoList(ArrayList<CatalogJoinBookkeepingInfo> catJoinBkkInfo) {
		this.catalogJoinBkkInfoList = catJoinBkkInfo;
	}
	
	public void addCatalogJoinBkkInfoList(CatalogJoinBookkeepingInfo info) {
		this.catalogJoinBkkInfoList.add(info);
	}
	
	public void removeCatalogJoinBkkInfoList(int index) {
		this.catalogJoinBkkInfoList.remove(index);
	}
	
	public ArrayList<Join> getJoinList() {
		return joinList;
	}

	public void setJoinList(ArrayList<Join> joinList) {
		this.joinList = joinList;
	}
	
	public String findJRightStreamAtIndex(int index) {
		return getJoinList().get(index).getjrightStream();
	}
	
	public String findJLeftStreamAtIndex(int index) {
		return getJoinList().get(index).getjleftStream();
	}

	/**
	 * Find all the stored info of a given stream in the catalog.
	 * 
	 * @param streamname the stream.
	 * @return The info of the stream in the catalog.
	 */
	public CatalogStream getStreamInfo(String streamname) {
		int i;
		for(CatalogStream catStream : getCatalogStreams()) {
			if (streamname.equals(catStream.getStreamName())) {
				return catStream;
			}
		}
		return null;
	}//end method getInfoForStream()
	
	public ArrayList<String> findJoinStreamsInBkkInfoListAtIndex(int index) {
		return getCatalogJoinBkkInfoList().get(index).getJoinStreams();
	}
	
	public String findAttributeInBkkInfoListAtIndex(int index) {
		return getCatalogJoinBkkInfoList().get(index).getAttributeName();
	}
	
	//not in use
	public String findJoinListMatchesWithAttribute(String streamname, String stemattribute) {
		int i;
		for(Join join : getJoinList()) {
			if (stemattribute.equals(join.getjattribute())) {
				
				if(streamname.equals(join.getjleftStream())) {
						return join.getjleftStream();
				} else if(streamname.equals(join.getjrightStream())) { 
					return join.getjrightStream();
				}//end if-else
			}
		}//end for-loop
		return null;
	}//end method getLeftStreamJoinListInfo()
	
	//not in use
	public Join findRightStreamJoinListMatchesWithAttribute(String streamname, String stemattribute) {
		for(Join join : getJoinList()) {
			if (streamname.equals(join.getjrightStream()) &&
					stemattribute.equals(join.getjattribute()) ) {
				return join;
			}
		}
		return null;
	}//end method getLeftStreamJoinListInfo()
	
	/**
	 * Find the index position of a specific attribute of the stream.
	 * 
	 * @param streamname the stream.
	 * @return The position of the attribute.
	 */
	public int findNoAttributeInStream(String streamname) {
		int no;
		no = getStreamInfo(streamname).findNoAttribute();
		
		return no;
	}//end method findIndexOfAttributeInStream()
	
	
	public void addSteMToBeStoredInStream(String streamname, int stemIndex) {
		getStreamInfo(streamname).addToSteMsToBeStored(stemIndex);
	}//end method addSteMToStoreInStream()
	
	
	public void addSteMToProbeInStream(String stemname, String stemattribute, int stemIndex) {
		for(Join join : getJoinList()) {
			if( join.getjrightStream().equals(stemname) &&
						stemattribute.equals(join.getjattribute()) ) {
							
				getStreamInfo(join.getjleftStream()).addToSteMsToProbe(stemIndex);
				
			} else if(join.getjleftStream().equals(stemname) &&
							stemattribute.equals(join.getjattribute()) ) {
				
				getStreamInfo(join.getjrightStream()).addToSteMsToProbe(stemIndex);
			}//end if-else		
		}//end for-loop
	}//end method addSteMToProbeInStream()
	
	public void addPredicateToBeScheduledInStream(String streamname, int predicateIndex) {
		getStreamInfo(streamname).addToPredicatesToBeScheduled(predicateIndex);
	}//end method addPredicateToBeScheduledInStream()
	
	/**
	 * Find the index position of a specific attribute of the stream.
	 * 
	 * @param streamname the stream.
	 * @param attributename the attribute of the stream.
	 * @return The position of the attribute.
	 */
	public int findIndexOfAttributeInStream(String streamname, String attributename) {
		int pos;
		pos = getStreamInfo(streamname).findIndexOfAttribute(attributename);
		
		return pos;
	}//end method findIndexOfAttributeInStream()
	
	/**
	 * Find the type of the attribute at the index position in the stream.
	 * 
	 * @param streamname the stream.
	 * @param index the index of the attribute.
	 * @return The attribute type.
	 */
	public String findAttributeTypeAtIndexInStream(String streamname, int index) {
		String type = getStreamInfo(streamname).getAttributeTypeAtIndex(index);
		
		return type;
	}//end method findAttributeTypeAtIndexInStream()

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Catalog [catalogStreams=" + catalogStreams
				+ ", arcatJoinBkkInfo=" + catalogJoinBkkInfoList + ", arjoinList="
				+ joinList + "]";
	}

}//end class Catalog
