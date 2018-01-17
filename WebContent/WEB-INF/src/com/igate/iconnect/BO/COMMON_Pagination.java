/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */
package com.igate.iconnect.BO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class COMMON_Pagination<E> implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 5515218280514778538L;
    private int pageNumber;
    private int pagesAvailable;
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    private List<E> pageItems = new ArrayList<E>();

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPagesAvailable(int pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesAvailable() {
        return pagesAvailable;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jul 19, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
