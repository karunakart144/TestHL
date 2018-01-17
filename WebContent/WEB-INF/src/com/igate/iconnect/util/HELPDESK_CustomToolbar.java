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

public class HELPDESK_CustomToolbar extends AbstractToolbar {
	private String userRole = "";
	private long menuid ;
	public HELPDESK_CustomToolbar(String role,long menuID){
		userRole = role;
		menuid=menuID;
	}
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
        
        if(menuid==149){
        	addToolbarItem(createCustomizebutton());
        	addToolbarItem(createSelectAllbutton());
        	addToolbarItem(createMultipleTicketClosureCustomItem());
        }else{
        
        addToolbarItem(createCustomItem());
       // addToolbarItem(createDelegateCustomItem());
       
        
        
        if(!userRole.equalsIgnoreCase("User")){
            addToolbarItem(createCustomizebutton());
        }
        
        }

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
        item.setTooltip("Approve ticket");
        item.setImage("images/approve_button.jpg");
        item.setAlt("custom");

        ToolbarItemRenderer renderer = new CustomItemRenderer(item, getCoreContext());
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        return item;
    }
    
   /* private ImageItem createDelegateCustomItem() {
        ImageItem item = new ImageItem();
     item.setCode("custom-Delegate");
        item.setTooltip("Delegate Approvals");
        item.setImage("images/delegate_button.jpg");
       item.setAlt("custom delegate");

        ToolbarItemRenderer renderer = new DelegateItemRenderer(item, getCoreContext());
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        return item;
    }*/
    
    private ImageItem createMultipleTicketClosureCustomItem() {
        ImageItem item = new ImageItem();
     item.setCode("custom-");
        item.setTooltip("Closure of Multiple Tickets");
        item.setImage("images/Close.jpg");
       item.setAlt("Closure of Multiple Tickets");

        ToolbarItemRenderer renderer = new MultipleClosureItemRenderer(item, getCoreContext());
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        return item;
    }
    
    private ImageItem createSelectAllbutton() {
        ImageItem item = new ImageItem();
     item.setCode("custom-");
        item.setTooltip("Select All");
        item.setImage("images/SelectAll.png");
       item.setAlt("Select All");

        ToolbarItemRenderer renderer = new SelectAllButton(item, getCoreContext());
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        return item;
    }
    
    
    private ImageItem createCustomizebutton() {
        ImageItem item = new ImageItem();
        item.setCode("custom-item");
        item.setTooltip("Customize list page");
        item.setImage("images/customise3.gif");
        item.setStyleClass("customizelistpage");
        item.setAlt("Customize list page");

        ToolbarItemRenderer renderer = new CustomizebuttonRenderer(item, getCoreContext());
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
            action.append("multiTicketUpdateApproval()");
            item.setAction(action.toString());
            return item.enabled();
        }
    }
    
    private static class DelegateItemRenderer extends AbstractItemRenderer {
        public DelegateItemRenderer(ToolbarItem item, CoreContext coreContext) {
            setToolbarItem(item);
            setCoreContext(coreContext);
        }

        public String render() {
            ToolbarItem item = getToolbarItem();
            StringBuilder action = new StringBuilder("javascript:");
            action.append("DelegateApproval()");
            item.setAction(action.toString());
            return item.enabled();
        }
    }
    
    private static class MultipleClosureItemRenderer extends AbstractItemRenderer {
        public MultipleClosureItemRenderer(ToolbarItem item, CoreContext coreContext) {
            setToolbarItem(item);
            setCoreContext(coreContext);
        }

        public String render() {
            ToolbarItem item = getToolbarItem();
            StringBuilder action = new StringBuilder("javascript:");
            action.append("showAllSelectedtickets()");
            item.setAction(action.toString());
            return item.enabled();
        }
    }
    
    private static class SelectAllButton extends AbstractItemRenderer {
        public SelectAllButton(ToolbarItem item, CoreContext coreContext) {
            setToolbarItem(item);
            setCoreContext(coreContext);
        }

        public String render() {
            ToolbarItem item = getToolbarItem();
            StringBuilder action = new StringBuilder("javascript:");
            action.append("selectAllTicket()");
            item.setAction(action.toString());
            return item.enabled();
        }
    }
    
    private static class CustomizebuttonRenderer extends AbstractItemRenderer {
        public CustomizebuttonRenderer(ToolbarItem item, CoreContext coreContext) {
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
