/**
 * The implementation of the system catalog.
 * <p>
 * It consists of three components:
 * <pre>The {@link datastorage.catalog.Catalog} class </pre> 
 * The implementation of the system <b>catalog</b>.
 * It consists of a <code>CatalogJoinBookkeepingInfo</code> Object, and
 * a <code>CatalogStream</code> Object
 * 
 * <pre>The {@link datastorage.catalog.CatalogJoinBookkeepingInfo} class </pre>
 * It performs bookkeeping of the information for the <b>joins</b> 
 * that exist in the execution plan.
 * 
 * <pre>The {@link datastorage.catalog.CatalogStream} class </pre>
 * It contains all the stored to the catalog information for a stream.
 * The name of the <b>stream</b>, the types and the values of the <b>attributes</b>,
 * and the <b>SteMs</b> and <b>predicates</b> where each tuple of this stream
 * should be routed to.
 */
package datastorage.catalog;