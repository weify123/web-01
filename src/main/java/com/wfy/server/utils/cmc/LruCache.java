package com.wfy.server.utils.cmc;

import java.util.LinkedHashMap;

public class LruCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = -9062508768983283300L;

	private int maxCapacity;

	public LruCache(int maxCapacity) {
		super(16, 0.75f, true);
		this.maxCapacity = maxCapacity;
	}

	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		if (super.size() > maxCapacity) {
			return true;
		}
		return false;
	}
}
