/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;

import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.LoginSQLQueryConstants;
import com.igate.iconnect.dao.LoginAuthenticationDAO;

@Transactional(rollbackFor = Exception.class)
public class LoginPatniAuthenticationDAOImpl implements
        LoginAuthenticationDAO {
    private LdapTemplate ldapTemplatePatni;
    private static final String SAME_ACCOUNT_NAME="samaccountname";
    private static final String EMPLOYEE_ID="employeeid";
    private static final String MOBILE="mobile";
    private static final String TEL_PHONE_NUM="telephonenumber";

    public LdapTemplate getLdapTemplatePatni() {
        return ldapTemplatePatni;
    }

    public void setLdapTemplatePatni(LdapTemplate ldapTemplatePatni) {
        this.ldapTemplatePatni = ldapTemplatePatni;
    }

    public boolean authenticate(String loginId, String passWord) {
        if (checkSpecialCharacter(loginId))
            return false;
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter(SAME_ACCOUNT_NAME, loginId));
        boolean authenticationOK = true;
        try {
            authenticationOK = ldapTemplatePatni.authenticate(
                    DistinguishedName.EMPTY_PATH, filter.toString(), passWord);
        } catch (AuthenticationException authException) {
            authenticationOK = false;
        }
        return authenticationOK;
    }

    // changes by mohit
    @SuppressWarnings("unchecked")
    public String getsamaccountname(String empid) {
        AndFilter filter = new AndFilter();
        List<String> empIdList = null;

        filter.and(new EqualsFilter(EMPLOYEE_ID, empid));

        empIdList = ldapTemplatePatni.search(DistinguishedName.EMPTY_PATH,
                filter.toString(), new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs)
                            throws javax.naming.NamingException {
                        String samaccountname = "";
                        samaccountname = attrs.get(SAME_ACCOUNT_NAME).get()
                                .toString();
                        return samaccountname;
                    }
                });
        if(empIdList.size()>1)
        {
      	  return empIdList.get(1);
        }
        else
        {
      	  return empIdList.get(0);  
        }
    }

    
    public String IsUserExist(final User userBeanObj) {
        if (checkSpecialCharacter(userBeanObj.getLoginId()))
            return null;
        List<String> membersList = new ArrayList<String>();
        
        membersList = ldapTemplatePatni.search(DistinguishedName.EMPTY_PATH,
                "(employeeid=" + userBeanObj.getLoginId() + ")", new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs)
                            throws javax.naming.NamingException {
                        String empid = attrs.get(EMPLOYEE_ID).get().toString();
                        //The below chnages has been done to fix the LDAP issue for two users
                        if(attrs.get(SAME_ACCOUNT_NAME)!=null){
                        empid +="-"+attrs.get(SAME_ACCOUNT_NAME).get().toString();
                        }
                        else{
                        	empid +=attrs.get(EMPLOYEE_ID).get().toString();
                        }
                        return empid;

                    }
                });
        if (membersList.size() == 0) {
            // get the employee id based on the username
            membersList = ldapTemplatePatni.search(
                    DistinguishedName.EMPTY_PATH, "(samaccountname=" + userBeanObj.getLoginId()
                            + ")", new AttributesMapper() {
                        public Object mapFromAttributes(Attributes attrs)
                                throws javax.naming.NamingException {
                            String empid = attrs.get(EMPLOYEE_ID).get()
                                    .toString();
                            empid +="-"+attrs.get(SAME_ACCOUNT_NAME).get().toString();
                            return empid;

                        }
                    });
        }
        if (membersList.size() > 0) {
            return membersList.get(0);
        } else {
            return null;
        }
    }

    
    public List<String> MobileAndExtn(String loginId) {
        final List<String> MobileAndExtn = new ArrayList<String>();
        List<String> ContactList = new ArrayList<String>();
        ContactList = ldapTemplatePatni.search(DistinguishedName.EMPTY_PATH,
                "(employeeid=" + loginId + ")", new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs)
                            throws javax.naming.NamingException {

                        String mobile = null;
                        if (attrs.get(MOBILE) != null) {
                            mobile = attrs.get(MOBILE).get().toString();
                            mobile = mobile.replaceAll(" ", "").replaceAll("/",
                                    "").replaceAll("-", "");
                            if (mobile.contains("+")) {
                                mobile = mobile
                                        .substring(mobile.indexOf("+") + 1);

                            }
                            if (!mobile.equalsIgnoreCase("")
                                    && mobile.charAt(0) == '0') {
                                mobile = mobile.substring(1);
                            }
                            MobileAndExtn.add(mobile);
                        } else {
                            MobileAndExtn.add("");
                        }

                        String extension = null;
                        if (attrs.get(TEL_PHONE_NUM) != null) {
                            extension = attrs.get(TEL_PHONE_NUM).get()
                                    .toString();
                            extension = extension.replaceAll(" ", "")
                                    .replaceAll("/", "").replaceAll("-", "");
                            if (extension.contains("+")) {
                                extension = extension.substring(extension
                                        .indexOf("+") + 1);

                            }
                            if (!extension.equalsIgnoreCase("")
                                    && extension.charAt(0) == '0') {
                                extension = extension.substring(1);
                            }
                            MobileAndExtn.add(extension);
                        } else {
                            MobileAndExtn.add("");
                        }
                        return MobileAndExtn;

                    }
                });

        if(!MobileAndExtn.isEmpty())
        {
        ContactList.clear();
        ContactList.add(MobileAndExtn.get(0));
        ContactList.add(MobileAndExtn.get(1));
        }
        else
        {
        	ContactList.clear();
            ContactList.add("");
            ContactList.add("");
        }
        return ContactList;
    }

    private boolean checkSpecialCharacter(String loginId) {
        String invalidEntries = LoginSQLQueryConstants.INVALID;
        boolean invalidFound = false;
        for (int i = 0; i < invalidEntries.length(); i++) {
            if (loginId.indexOf(invalidEntries.charAt(i)) != -1) {
                invalidFound = true;
                break;
            }
        }
        return invalidFound;

    }

	public String getsamaccountnameforOrchestration(String loginID) {
		// TODO Auto-generated method stub
		return null;
	}

}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 17, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
