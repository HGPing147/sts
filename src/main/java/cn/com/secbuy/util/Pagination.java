package cn.com.secbuy.util;

/**
 * @ClassName: Page
 * @Description: TODO(分页类,记录分页常用数据)
 * @author fan
 * @date 2015年3月3日 下午10:36:56
 * @version V1.0
 */
public class Pagination {
	private long pageNow;
	private long rows;
	private long pages;
	public static int PAGESIZE = 12;

	public Pagination() {
	}

	public Pagination(long pageNow, long rows) {
		super();
		this.pageNow = pageNow;
		this.rows = rows;
	}

	public long getPageNow() {
		return pageNow;
	}

	public void setPageNow(long pageNow) {
		this.pageNow = pageNow;
	}

	public long getPages() {
		pages = rows % PAGESIZE == 0 ? (rows / PAGESIZE) : (rows / PAGESIZE + 1);
		return pages;
	}

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

}
