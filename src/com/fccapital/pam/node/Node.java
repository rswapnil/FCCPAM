/**
 * Node in a Company Tree Structure 
 * Can be Company, Client or Node
 */
package com.fccapital.pam.node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Swapnil
 *
 */
public class Node {
	
	private String nodeName;
	private NodeType type;
	private BigDecimal transactionAmount = BigDecimal.ZERO;
	private BigDecimal profit = BigDecimal.ZERO;
	
	private BigDecimal allocatedProfit = BigDecimal.ZERO;	
	private List<Node> children;
	
	public Node(NodeBuilder builder) {
		this.nodeName = builder.nodeName;
		this.type = builder.type;
		this.transactionAmount = builder.transactionAmount;
		this.children = builder.children;
	}	
	
	public static class NodeBuilder {
		
		private String nodeName;
		private NodeType type;
		private BigDecimal transactionAmount;
		private List<Node> children = new ArrayList<Node>();
		
		public NodeBuilder withChildren(List<Node> children) {
			this.children = children;
			return this;
		}
		
		public NodeBuilder withType(NodeType type) {
			this.type = type;
			return this;
		}
		
		public NodeBuilder withName(String nodeName) {
			this.nodeName = nodeName;
			return this;
		}
		
		public NodeBuilder withTransactionAmount(BigDecimal transactionAmount) {
			this.transactionAmount = transactionAmount;
			return this;
		}
		
		public Node build() {
			return new Node(this);
		}
	}
	
	public String getNodeName() {
		return nodeName;
	}

	public boolean isCompany() {
		return NodeType.COMPANY == this.type;
	}

	public boolean isClient() {
		return NodeType.CLIENT == this.type;
	}

	public boolean isNode() {
		return NodeType.NODE == this.type;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public BigDecimal getAllocatedProfit() {
		return allocatedProfit;
	}

	public List<Node> getChildren() {
		return children;
	}	

	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}
	
    public Stream<Node> flattened() {
        return Stream.concat(
                Stream.of(this),
                children.stream().flatMap(Node::flattened));
    }	

	public void setAllocatedProfit(BigDecimal allocatedProfit) {
		this.allocatedProfit = allocatedProfit;
	}
	
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	
	@Override
	public String toString() {
		return "[" +this.nodeName + ":" + this.type + "]";
	}	

}
