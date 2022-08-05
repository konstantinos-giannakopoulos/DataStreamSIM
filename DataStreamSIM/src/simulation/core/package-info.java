/**
 * It contains the main structures of the simulation.
 * <p>
 * It consists of three components:
 * <pre>The {@link simulation.core.Clock} class </pre> 
 * It is the <b>system clock</b> of the simulation.
 * It also contains the statistics.
 * 
 * <pre>The {@link simulation.core.EventList} class </pre>
 * It is the <b>queue of events</b> of the simulation.
 * 
 * <pre>The {@link simulation.core.Plan} class </pre>
 * It parses the execution plan and retrieves the 
 * information about the <b>windows</b> and the 
 * <b>operators</b> it contains.
 * 
 * <pre>The {@link simulation.core.SimulationParameters} class </pre>
 * The simulation tune-up parameters used by 
 * both frameworks.
 */
package simulation.core;