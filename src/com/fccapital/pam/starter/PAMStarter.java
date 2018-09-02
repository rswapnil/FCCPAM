/**
 * Profit Allocation Mechanism Application Starter
 * Goal is to process profit allocation for a given tree structure and print it
 * This class will initialize sample company tree and invoke Profit Allocation 
 * and print the allocated profit for all the nodes
 * 
 * Below Sample Tree Structure Assumed for this solution
 * 
 * 
 *  Root Level                                 Company(Root Node)
 *                                           
 *  Level 1                            Client-1                       Client 2
 *  
 *  Level 2                 C1-Node-A        C1-Node-B          C2-Node-A       C2-Node-B
 *  
 *  Level 3            C1-Node-A-1     C1-Node-A-2       C2-Node-A-1   C2-Node-A-2     
 * 
 */
package com.fccapital.pam.starter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import com.fccapital.pam.node.Node;
import com.fccapital.pam.node.NodeType;
import com.fccapital.pam.profit.TreeProfitAllocation;

/**
 * @author Swapnil
 *
 */
public class PAMStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		// generate sample tree		
		Node company = generateCompanyTree();
		
		// invoke pam
		new TreeProfitAllocation().process(company);				
		
		// print profit for all tree
		company.flattened().forEach(n -> 
		System.out.println(n.toString() + " Profit Allocated:" + n.getAllocatedProfit().setScale(2, RoundingMode.HALF_EVEN)));

	}

	private static Node generateCompanyTree() {
		// create Client 1 tree
		Node c1NodeA1 = new Node.NodeBuilder().withName("C1-Node-A-1").withType(NodeType.NODE).build();
		Node c1NodeA2 = new Node.NodeBuilder().withName("C1-Node-A-2").withType(NodeType.NODE).build();
		Node c1NodeA = new Node.NodeBuilder().withName("C1-Node-A").withType(NodeType.NODE)
				.withChildren(Arrays.asList(c1NodeA1, c1NodeA2)).build();
		Node c1NodeB = new Node.NodeBuilder().withName("C1-Node-B").withType(NodeType.NODE).build();
		Node client1 = new Node.NodeBuilder().withName("Client-1").withType(NodeType.CLIENT)
				.withTransactionAmount(new BigDecimal(4000))
				.withChildren(Arrays.asList(c1NodeA, c1NodeB)).build();

		// create client 2 tree
		Node c2NodeA1 = new Node.NodeBuilder().withName("C2-Node-A-1").withType(NodeType.NODE).build();
		Node c2NodeA2 = new Node.NodeBuilder().withName("C2-Node-A-2").withType(NodeType.NODE).build();
		Node c2NodeA = new Node.NodeBuilder().withName("C2-Node-A").withType(NodeType.NODE)
				.withChildren(Arrays.asList(c2NodeA1, c2NodeA2)).build();
		Node c2NodeB = new Node.NodeBuilder().withName("C2-Node-B").withType(NodeType.NODE).build();
		Node client2 = new Node.NodeBuilder().withName("Client-2").withType(NodeType.CLIENT)
				.withTransactionAmount(new BigDecimal(4000))
				.withChildren(Arrays.asList(c2NodeA, c2NodeB)).build();
				
		// create root node - company
		Node company = new Node.NodeBuilder().withName("FC-Group").withType(NodeType.COMPANY)
				.withChildren(Arrays.asList(client1, client2)).build();
		return company;
	}

}
