/**
 * This is the brain of the simulation.
 * <p>
 * It consists of three components:
 * 
 * <pre>The {@link simulation.brain.iDataStreamSIM} class </pre> 
 * It is the interface for the below components of the package.
 * 
 * <pre>The {@link simulation.brain.Engine} class </pre> 
 * Analyzes the execution plan, builds the <b>SteMs</b> and <b>predicates</b>,
 * and decides where to <b>schedule</b> each incoming tuple.
 * Starts the <b>scheduler</b> of the system, collects its results and terminates it at the end.
 * Apply the <b>projections</b> from the execution plan to the output tuples of the simulation.
 * 
 * <pre>The {@link simulation.brain.Scheduler} class </pre>
 * Loads the <b>windows</b> of the simulation with incoming tuples, and
 * it performs the <b>routing</b> of each tuple.
 */
package simulation.brain;