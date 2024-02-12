package net.sf.jasperreports.search;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRRuntimeException;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 * @version $Id$
 */
public class HitTermInfo implements JRCloneable {

	private int position;
	private int start;
	private int end;
	private String value;
	private String pageNo;

	public HitTermInfo(int position, int start, int end, String value) {
		this.position = position;
		this.start = start;
		this.end = end;
		this.value = value;
	}

	public int getPosition() {
		return position;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getEnd() {
		return end;
	}

	public String getValue() {
		return value;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageNo() {
		return pageNo;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// never
			throw new JRRuntimeException(e);
		}
	}
}
