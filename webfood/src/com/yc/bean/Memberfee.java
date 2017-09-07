package com.yc.bean;

public class Memberfee {
	
	private int year;
	private int shouldPay;
	private int truePay;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getShouldPay() {
		return shouldPay;
	}
	public void setShouldPay(int shouldPay) {
		this.shouldPay = shouldPay;
	}
	public int getTruePay() {
		return truePay;
	}
	public void setTruePay(int truePay) {
		this.truePay = truePay;
	}
	@Override
	public String toString() {
		return "memberfee [year=" + year + ", shouldPay=" + shouldPay + ", truePay=" + truePay + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + shouldPay;
		result = prime * result + truePay;
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Memberfee other = (Memberfee) obj;
		if (shouldPay != other.shouldPay)
			return false;
		if (truePay != other.truePay)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	public Memberfee(int year, int shouldPay, int truePay) {
		super();
		this.year = year;
		this.shouldPay = shouldPay;
		this.truePay = truePay;
	}
	public Memberfee() {
		super();
	}
	
	
	
	
}
