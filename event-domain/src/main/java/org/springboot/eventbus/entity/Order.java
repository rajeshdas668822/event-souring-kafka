package org.springboot.eventbus.entity;

import javax.persistence.*;
import java.io.*;

@Entity
@Table(name = "T_ORDER")
@NamedQuery(name="order.findOrderByID",  query="SELECT o FROM Order o   WHERE o.orderId = :orderId")
public class Order implements Externalizable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORDER_ID",unique = true, nullable = false)
	private String orderId;

	@Column(name = "PRODUCT_TYPE")
	private String productType;

	@Column(name = "IS_STANDALONE")
	private Boolean isStandalone;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "QUANTITY")
	private Double quantity;

	@Column(name = "COUNTER_PARTY")
	private String counterParty;


	@Column(name = "COST_PRICE")
	private Double costPrice;

	@Column(name = "STATUS")
	private String status;

	@Transient
	private String taskId;

	@Column(name = "FILLAMOUNT")
	private Double fillAmount;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Boolean getStandalone() {
		return isStandalone;
	}

	public void setStandalone(Boolean standalone) {
		isStandalone = standalone;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Double getFillAmount() {
		return fillAmount;
	}

	public void setFillAmount(Double fillAmount) {
		this.fillAmount = fillAmount;
	}



	@Override
	public void writeExternal(ObjectOutput out) throws IOException {

		out.writeObject(orderId);
		out.writeObject(productType);
		out.writeObject(amount);
		out.writeObject(quantity);
		out.writeObject(counterParty);
		out.writeObject(costPrice);
		out.writeObject(status);
		out.writeObject(fillAmount);

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.orderId = (String)in.readObject();
		this.productType = (String)in.readObject();
		this.amount = (Double)in.readObject();
		this.quantity = (Double)in.readObject();
		this.counterParty = (String)in.readObject();
		this.costPrice = (Double)in.readObject();
		this.status = (String)in.readObject();
		this.fillAmount = (Double)in.readObject();
	}
}
