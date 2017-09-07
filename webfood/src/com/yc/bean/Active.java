package com.yc.bean;

public class Active {
	private Integer active_id;
	private Double activeLimit;
	private Double activeReduce;
	
	public Integer getActive_id() {
		return active_id;
	}
	public void setActive_id(Integer active_id) {
		this.active_id = active_id;
	}
	public Double getActiveLimit() {
		return activeLimit;
	}
	public void setActiveLimit(Double activeLimit) {
		this.activeLimit = activeLimit;
	}
	public Double getActiveReduce() {
		return activeReduce;
	}
	public void setActiveReduce(Double activeReduce) {
		this.activeReduce = activeReduce;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeLimit == null) ? 0 : activeLimit.hashCode());
		result = prime * result + ((activeReduce == null) ? 0 : activeReduce.hashCode());
		result = prime * result + ((active_id == null) ? 0 : active_id.hashCode());
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
		Active other = (Active) obj;
		if (activeLimit == null) {
			if (other.activeLimit != null)
				return false;
		} else if (!activeLimit.equals(other.activeLimit))
			return false;
		if (activeReduce == null) {
			if (other.activeReduce != null)
				return false;
		} else if (!activeReduce.equals(other.activeReduce))
			return false;
		if (active_id == null) {
			if (other.active_id != null)
				return false;
		} else if (!active_id.equals(other.active_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Active [active_id=" + active_id + ", activeLimit=" + activeLimit + ", activeReduce=" + activeReduce
				+ "]";
	}
	
	

}
