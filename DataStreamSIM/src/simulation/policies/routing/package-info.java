/**
 * It contains the routing policies that this simulation supports.
 * <p>
 * It supports two routing policies:
 * <pre>The {@link simulation.policies.routing.PriorityPolicy} class </pre> 
 * It is used by the <i>static framework</i>. All the operators of the 
 * execution plan are assigned priorities the way they appear, so that 
 * they are executed in order. 
 * 
 * <pre>The {@link simulation.policies.routing.TicketPolicy} class </pre>
 * It is used by the <i>adaptive framework</i>.
 * The SteMs are assigned <b>tickets</b> through the execution, and the 
 * router holds a lottery to choose the next one for execution. 
 */
package simulation.policies.routing;