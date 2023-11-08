package com.myorg.props;

import software.amazon.awscdk.StackProps;

public class Ec2StackProps implements StackProps {
    private String iamRoleName;
    private String vpcId;

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getIamRoleName() {
        return iamRoleName;
    }

    public void setIamRoleName(String iamRoleName) {
        this.iamRoleName = iamRoleName;
    }
}
