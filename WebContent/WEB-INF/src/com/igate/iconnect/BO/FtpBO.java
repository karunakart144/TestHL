/*
 * Copyright (c) 2011.Information Systems(iGATE Corporation Ltd)
 */

package com.igate.iconnect.BO;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;

public class FtpBO  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -4772918326788650178L;
    @Value("${ftpIp}")
    private String ftpIP;
    @Value("${ftpUser}")
    private String ftpUser;
    @Value("${ftpPass}")
    private String ftpPassword;

    public String getFtpIP() {
        return ftpIP;
    }

    public void setFtpIP(String ftpIP) {
        this.ftpIP = ftpIP;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }


}
