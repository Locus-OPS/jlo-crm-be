package th.co.locus.jlo.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.ToString;

@ToString
public class Page<T> {

	private final List<T> content = new ArrayList<T>();
	private final long total;
	private final PageRequest pageRequest;
	
	public Page(List<T> content, PageRequest pageRequest, long total) {
		this.content.addAll(content);
		this.pageRequest = pageRequest;
		this.total = !content.isEmpty() && pageRequest != null && pageRequest.getOffset() + pageRequest.getPageSize() > total
				? pageRequest.getOffset() + content.size() : total;
	}
	
	public Page(List<T> content) {
		this(content, null, null == content ? 0 : content.size());
	}
	
	public int getTotalPages() {
		return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
	}

	public long getTotalElements() {
		return total;
	}
	
	public boolean hasNext() {
		return getNumber() + 1 < getTotalPages();
	}

	public boolean isLast() {
		return !hasNext();
	}
	
	public int getNumber() {
		return pageRequest == null ? 0 : pageRequest.getPageNumber();
	}

	public int getSize() {
		return pageRequest == null ? 0 : pageRequest.getPageSize();
	}
	
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}
	
}
