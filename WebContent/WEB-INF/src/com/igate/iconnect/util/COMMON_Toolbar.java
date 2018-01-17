/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Row;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.toolbar.AbstractItemRenderer;
import org.jmesa.view.html.toolbar.AbstractToolbar;
import org.jmesa.view.html.toolbar.ImageItem;
import org.jmesa.view.html.toolbar.MaxRowsItem;
import org.jmesa.view.html.toolbar.TextItem;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemRenderer;
import org.jmesa.view.html.toolbar.ToolbarItemType;
import org.jmesa.web.WebContext;

public class COMMON_Toolbar extends AbstractToolbar {
    @Override
    public String render() {
        addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);
        addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);
        addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);
        
        addToolbarItem(ToolbarItemType.SEPARATOR);

        MaxRowsItem maxRowsItem = (MaxRowsItem) addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);
        if (getMaxRowsIncrements() != null) {
            maxRowsItem.setIncrements(getMaxRowsIncrements());
        }
        
        boolean exportable = ViewUtils.isExportable(getExportTypes());

        if (exportable) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
            addExportToolbarItems(getExportTypes());
        }
        
        Row row = getTable().getRow();
        List columns = row.getColumns();
        
        boolean filterable = ViewUtils.isFilterable(columns);

        if (filterable) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
            addToolbarItem(ToolbarItemType.FILTER_ITEM);
            addToolbarItem(ToolbarItemType.CLEAR_ITEM);
        }
        
        boolean editable = ViewUtils.isEditable(getCoreContext().getWorksheet());

        if (editable) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
            //addToolbarItem(ToolbarItemType.SAVE_WORKSHEET_ITEM);
            //addToolbarItem(ToolbarItemType.FILTER_WORKSHEET_ITEM);
        }
        
        addToolbarItem(ToolbarItemType.SEPARATOR);
        
        addToolbarItem(createCustomItem());

        return super.render();
    }
    
    private TextItem createSomething(){
        TextItem obj = new TextItem();
        obj.setCode("<input type = \"text\"></input>");
        ToolbarItemRenderer renderer = new CustomItemRenderer(obj, getCoreContext());
        obj.setToolbarItemRenderer(renderer);
        return obj;
    }
    
    private ImageItem createCustomItem() {
        ImageItem item = new ImageItem();
        item.setCode("custom-item");
        item.setTooltip("Customize list page");
        item.setImage("images/customise3.gif");
        item.setStyleClass("customizelistpage");
        item.setAlt("Customize list page");

        ToolbarItemRenderer renderer = new CustomItemRenderer(item, getCoreContext());
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        return item;
    }
    
    private static String getImage(String image, WebContext webContext, CoreContext coreContext) {
        String imagesPath = HtmlUtils.imagesPath(webContext, coreContext);
        return imagesPath + image;
    }
    
    private static class CustomItemRenderer extends AbstractItemRenderer {
        public CustomItemRenderer(ToolbarItem item, CoreContext coreContext) {
            setToolbarItem(item);
            setCoreContext(coreContext);
        }

        public String render() {
            ToolbarItem item = getToolbarItem();
            StringBuilder action = new StringBuilder("javascript:");
            action.append("displayCustomization()");
            item.setAction(action.toString());
            return item.enabled();
        }
    }
    
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jul 29, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
