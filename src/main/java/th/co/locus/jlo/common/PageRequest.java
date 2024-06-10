package th.co.locus.jlo.common;

import lombok.ToString;

@ToString
public class PageRequest {
	
	private final int page;
	private final int size;

	public PageRequest(int page, int size) {
		if (page < 0) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}

		if (size < 1) {
			throw new IllegalArgumentException("Page size must not be less than one!");
		}

		this.page = page;
		this.size = size;
	}
	
	public int getPageSize() {
		return size;
	}

	public int getPageNumber() {
		return page;
	}

	public int getOffset() {
		return page * size;
	}
	
	public boolean hasPrevious() {
		return page > 0;
	}
	
	public PageRequest next() {
		return new PageRequest(getPageNumber() + 1, getPageSize());
	}

	public PageRequest previous() {
		return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize());
	}
	
	public PageRequest first() {
		return new PageRequest(0, getPageSize());
	}

}
