package com.example.demo.config;

import com.example.demo.modules.entity.user.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RemotePropertiesConfig {


    @Value("${adminPath}")
    private String adminPath;

    @Value("${tron.trx_address:#{null}}")
    private String trxAddress;

    @Value("${tron.contract_address:#{null}}")
    private String contractAddress;

    @Value("${tron.privateKey:#{null}}")
    private String privateKey;

    public String getTrxAddress() {
        return trxAddress;
    }

    public void setTrxAddress(String trxAddress) {
        this.trxAddress = trxAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * @return String return the adminPath
     */
    public String getAdminPath() {
        return adminPath;
    }

    protected String getUserCode() {
        return getUser().getUserName();
    }

    /**
     * @param adminPath the adminPath to set
     */
    public void setAdminPath(String adminPath) {
        this.adminPath = adminPath;
    }


}
