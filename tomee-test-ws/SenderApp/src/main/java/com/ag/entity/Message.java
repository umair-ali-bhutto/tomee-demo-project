package com.ag.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the MESSAGE database table.
 * 
 */

@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
	@SequenceGenerator(name = "MESSAGE_SEQ", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
	private int id;

	@Column(name = "AUDIT_LOG_ID")
	private int auditLogId;

	@Column(name = "ENTRY_DATE")
	private Timestamp entryDate;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "IS_SENT")
	private int isSent;

	@Column(name = "IS_RECIEVED")
	private int isRecieved;

	@Column(name = "RECIEVED_DATE")
	private Timestamp recievedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuditLogId() {
		return auditLogId;
	}

	public void setAuditLogId(int auditLogId) {
		this.auditLogId = auditLogId;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIsSent() {
		return isSent;
	}

	public void setIsSent(int isSent) {
		this.isSent = isSent;
	}

	public int getIsRecieved() {
		return isRecieved;
	}

	public void setIsRecieved(int isRecieved) {
		this.isRecieved = isRecieved;
	}

	public Timestamp getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(Timestamp recievedDate) {
		this.recievedDate = recievedDate;
	}

}