package com.cs322.ors.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	// @JsonIgnoreProperties({"password", "role","closed"})
	@JoinColumn(name = "customer_id")
	@JsonIdentityReference(alwaysAsId = true)
	private User customer;

	@Column(name="date")
	private LocalDateTime date;

	private int type; // 0 = pick-up, 1 = delivery, 2 = reservation
	private boolean DishRated;
	private boolean DelivererRated;
	private boolean userRated;
	private boolean completed;
	private boolean cancelled;

	// Bidirectional Mapping

	@JsonIgnore
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private ChefJob chefJob;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DishOrder> dishOrders = new ArrayList<>();


	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private DeliveryStatus deliveryStatus;
	
	@JsonIgnore
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DeliveryJobs deliveryJobs;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UserRatings> userRating = new ArrayList<>();
	
	@JsonIgnore
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Reservation reservation;

//	@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
////	@JsonIdentityReference(alwaysAsId = true)
	@JsonIgnoreProperties({"password", "role", "closed", "deliveryJobs"})
	@OneToOne
	private User deliveryPerson; 

	public Order() {
	}

	public Order(User customer, int type) {
		super();
		this.customer = customer;
		this.type = type;
		this.date = LocalDateTime.now();
		this.completed = false;
		this.cancelled = false;
		this.DishRated = false;
		this.DelivererRated = false;
		this.userRated = false;
	}
	

	public List<DishOrder> getDishOrders() {
		return dishOrders;
	}

	public void setDishOrders(List<DishOrder> dishOrders) {
		this.dishOrders = dishOrders;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public boolean getCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	

	public boolean getUserRated() {
		return userRated;
	}

	public void setUserRated(boolean userRated) {
		this.userRated = userRated;
	}

	public boolean getDishRated() {
		return DishRated;
	}

	public void setDishRated(boolean DishRated) {
		this.DishRated = DishRated;
	}

	public boolean getDelivererRated() {
		return DelivererRated;
	}

	public void setDelivererRated(boolean DelivererRated) {
		this.DelivererRated = DelivererRated;
	}

	public boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isCancelled() {
		return cancelled;
	}
	public DeliveryJobs getDeliveryJob() {
		return deliveryJobs;
	}
	
	public ChefJob getChefJob() {
		return chefJob;
	}

	public void setChefJob(ChefJob chefJob) {
		this.chefJob = chefJob;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public User getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(User deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", date=" + date + ", type=" + type + ", completed="
				+ completed + ", cancelled=" + cancelled + "]";
	}

}
