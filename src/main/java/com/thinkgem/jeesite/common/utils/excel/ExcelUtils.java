package com.thinkgem.jeesite.common.utils.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelUtils {
    private Workbook rwb = null;

    /**
     * 
     * 根据查询条件获取某一行某几列的单元格内容，并按顺序存放在List中返回
     * 
     * @param excelConditional
     * @param excel
     * @return
     */
    public List<String> getFieldStrByExcelConditional(ExcelConditionalInfo excelConditional, File excel) {
        if (rwb == null) {
            try {
                rwb = Workbook.getWorkbook(excel);
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (rwb == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        int line = excelConditional.getLine();
        for (int i = excelConditional.getMincol(); i <= excelConditional.getMaxcol(); i++) {
            Sheet sheet = rwb.getSheet(excelConditional.getSheetName());
            list.add(sheet.getCell(i, line).getContents());
        }
        return list;
    }

    /**
     * 
     * 获取某个格子的文本块
     * 
     * @param excelConditional
     *            sheetname 表名 minCol 定位列 line定位行
     * @param excel
     * @return
     */
    public String getCellContext(ExcelConditionalInfo excelConditional, File excel) {
        Workbook rwb = null;
        try {
            rwb = Workbook.getWorkbook(excel);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rwb.getSheet(excelConditional.getSheetName())
                .getCell(excelConditional.getMincol(), excelConditional.getLine()).getContents();

    }

    /**
     * 判断xls文件中是否存在对应的表
     * 
     * @param excel
     * @param sheetName
     * @return
     */
    public boolean sheetExits(File excel, String sheetName) {
        Workbook rwb = null;
        try {
            rwb = Workbook.getWorkbook(excel);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rwb == null) {
            return false;
        }
        return rwb.getSheet(sheetName) != null;

    }

    public static void main(String[] args) {
    }

}
