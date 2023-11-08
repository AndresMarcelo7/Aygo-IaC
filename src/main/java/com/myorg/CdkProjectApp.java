package com.myorg;

import com.myorg.props.Ec2StackProps;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class CdkProjectApp {
    public static void main(final String[] args) {
        App app = new App();
        Ec2StackProps props = new Ec2StackProps();
        props.setVpcId("vpc-070983c09ecbbefad");
        props.setIamRoleName("LabRole");

        new CdkProjectStack(app, "CdkProjectStack", StackProps.builder().env(Environment.builder()
                .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                .region(System.getenv("CDK_DEFAULT_REGION"))
                .build()).build(), props);

        app.synth();
    }
}

