package com.thinkgem.jeesite.common.utils.excel;

/**
 * 表格读取定位
 * 
 * @author Administrator
 *
 */
public class ExcelConditionalInfo {
    private String sheetName;// 表名

    private int line;// 第几行

    private int mincol;// 从左侧列

    private int maxcol;// 到右侧列

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getMincol() {
        return mincol;
    }

    public void setMincol(int mincol) {
        this.mincol = mincol;
    }

    public int getMaxcol() {
        return maxcol;
    }

    public void setMaxcol(int maxcol) {
        this.maxcol = maxcol;
    }

    @Override
    public String toString() {
        return "ExcelConditionalInfo [sheetName=" + sheetName + ", line=" + line + ", mincol=" + mincol + ", maxcol="
                + maxcol + "]";
    }

}
