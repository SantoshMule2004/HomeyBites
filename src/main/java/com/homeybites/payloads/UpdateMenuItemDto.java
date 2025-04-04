package com.homeybites.payloads;

import java.util.ArrayList;
import java.util.List;

public class UpdateMenuItemDto {
	List<Integer> oldIds = new ArrayList<>();
	List<Integer> newIds = new ArrayList<>();
	
	public List<Integer> getOldIds() {
		return oldIds;
	}
	public void setOldIds(List<Integer> oldIds) {
		this.oldIds = oldIds;
	}
	public List<Integer> getNewIds() {
		return newIds;
	}
	public void setNewIds(List<Integer> newIds) {
		this.newIds = newIds;
	}
	
}
