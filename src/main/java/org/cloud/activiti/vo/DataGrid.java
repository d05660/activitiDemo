package org.cloud.activiti.vo;

import java.util.List;

public class DataGrid<T> {
    private int current;
    private int rowCount;
    private long total;
    private List<T> rows;
    
    public DataGrid() {
        super();
    }

    public DataGrid(int current, int rowCount, long total, List<T> rows) {
        super();
        this.current = current;
        this.rowCount = rowCount;
        this.total = total;
        this.rows = rows;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
