/**
 * Tree Profit Allocation Process
 * 
 * Tree Profit allocation mechanism support the following scenario:
 * The company has a Tree structure for profit distribution:
 * The Node (may have other role Nodes OR the Clients)
 * The Client is someone who does transactions with an amount in dollars.
 * The Profit is calculated as multiplication of transaction amount by 0.025(transaction processing fee).
 * On the node we have the profit distribution ratio – the node keeps X% and gives down the tree (100-X)%
 * The clients are not involved into the profit distribution, its parent keeps 100% of allocated profit
 * 
 *                                           Company(Root Node)
 *                                           
 *  Level 1                            Client-1               Client 2
 *  
 *  Level 2                      C1-Node-A   C1-Node-B    C2-Node-A   C2-Node-B
 *  
 *  Level 3            C1-Node-A-1     C1-Node-A-2  
 * 
 */
package com.fccapital.pam.profit;

import java.math.BigDecimal;
import java.util.List;

import com.fccapital.pam.node.Node;

import static com.fccapital.pam.Constants.*;

/**
 * @author Swapnil
 *
 */
public class TreeProfitAllocation implements IProfitAllocation {
	
	public void process(Node node) {
		
		if (node.isCompany()) {
			calculateClientProfit(node.getChildren());
			calculateCompanyAllocation(node);
		}
		
		if (node.isClient()) {
			calculateClientAllocation(node);
		}
		
		if (node.isNode()) {
			calculateNodeAllocation(node);						
		}
		
		if (node.hasChildren()) {
			node.getChildren().stream().forEach(this::process);
		}		
		
	}

	/**
	 * The clients are not involved into the profit distribution, its parent keeps 100% of allocated profit
	 * 
	 * Assumption/Interpreted here that client doesn't involve in profit distribution but then it has to pass on 100%
	 * profit equally to it children node so that they can further participate in distribution based on X% 
	 * 
	 * @param node
	 */
	private void calculateClientAllocation(Node node) {
		node.setAllocatedProfit(BigDecimal.ZERO);
		// Now pass on 100% client profit to its child nodes if any
		if (!node.hasChildren()) {
			return;
		}
		BigDecimal childNodeProfit = node.getProfit().divide(new BigDecimal(node.getChildren().size()));
		node.getChildren().forEach(n -> n.setProfit(childNodeProfit));
		
	}

	/**
	 * The clients are not involved into the profit distribution, its parent keeps 100% of allocated profit
	 * @param node
	 */
	private void calculateCompanyAllocation(Node node) {
		BigDecimal companyProfitAllocation = node.getChildren().stream().map(n -> n.getProfit()).reduce(BigDecimal.ZERO, BigDecimal::add);
		node.setAllocatedProfit(companyProfitAllocation);
		
	}

	/**
	 * The Profit is calculated as multiplication of transaction amount by 0.025(transaction processing fee).
	 * @param children
	 */
	private void calculateClientProfit(List<Node> children) {
		children.forEach(n -> n.setProfit(TRANSACTION_PROCESSING_FEE.multiply(n.getTransactionAmount())));
		
	}

	/**
	 * On the node we have the profit distribution ratio – the node keeps X% and gives down the tree (100-X)%
	 * 
	 * Assumption 1. if there are no children node keeps 100% allocation
	 * Assumption 2. if there are children node passes on (100-X)% equally to its children
	 * @param node
	 */
	private void calculateNodeAllocation(Node node) {
		if (!node.hasChildren()) {
			node.setAllocatedProfit(node.getProfit());
			return;
		}
		
		node.setAllocatedProfit(node.getProfit().multiply(PROFIT_DISTRIBUTION_RATIO));
		// pass (100-X)% to children
		BigDecimal remainingProfit = node.getProfit().subtract(node.getAllocatedProfit()); 
		BigDecimal childNodeProfit = remainingProfit.divide(new BigDecimal(node.getChildren().size()));
		node.getChildren().forEach(n -> n.setProfit(childNodeProfit));				
		
	}
}
