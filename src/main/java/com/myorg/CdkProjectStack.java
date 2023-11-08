package com.myorg;

import com.myorg.props.Ec2StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.iam.Role;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.HashMap;
import java.util.Map;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class CdkProjectStack extends Stack {

    Ec2StackProps ec2Properties;

    public CdkProjectStack(final Construct scope, final String id, final StackProps props, Ec2StackProps ec2Props) {
        super(scope, id, props);
        ec2Properties = ec2Props;
        // The code that defines your stack goes here

        // example resource
        // final Queue queue = Queue.Builder.create(this, "CdkProjectQueue")
        //         .visibilityTimeout(Duration.seconds(300))
        //         .build();
        Integer NumberOfEc2Instances = 3;
        IVpc vpc = Vpc.fromLookup(this, "VPC", VpcLookupOptions.builder().vpcId(ec2Props.getVpcId()).build());
        SecurityGroup securityGroup = createSecurityGroup(vpc);

        for (int i = 0; i < NumberOfEc2Instances; i++) {
            createEc2Instance("Instance" + i, vpc, securityGroup);
        }
    }

    private SecurityGroup createSecurityGroup(IVpc vpc) {
        SecurityGroup securityGroup = SecurityGroup.Builder.create(this, "SecurityGroup")
                .vpc(vpc)
                .securityGroupName("CdkProjectSecurityGroup")
                .allowAllOutbound(true)
                .build();
        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(22), "ssh from anywhere!");
        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "http from anywhere!");
        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(8080), "http from anywhere!");
        return securityGroup;
    }

    private Instance createEc2Instance(String id, IVpc vpc, SecurityGroup sg){
        final Map<String, String> amazonLinuxAMIs = new HashMap<>();
        amazonLinuxAMIs.put("us-east-1", "ami-05c13eab67c5d8861");

        final IMachineImage armUbuntuMachineImage = MachineImage.genericLinux(amazonLinuxAMIs);

        final Instance engineEC2Instance = Instance.Builder.create(this,  id + "-ec2")
                .instanceName(id + "-ec2")
                .machineImage(armUbuntuMachineImage)
                .securityGroup(sg)
                .instanceType(InstanceType.of(
                        InstanceClass.T3,
                        InstanceSize.MICRO
                ))
                .vpcSubnets(
                        SubnetSelection.builder()
                                .subnetType(SubnetType.PUBLIC)
                                .build()
                )
                .vpc(vpc)
                .role(Role.fromRoleName(this, id+"Role", ec2Properties.getIamRoleName()))
                .build();
        String userData = "#!/bin/bash\n" +
                "yum update -y\n" +
                "sudo yum install docker -y\n" +
                "sudo service docker start\n" +
                "sudo usermod -a -G docker ec2-user\n" +
                "sudo docker run -d -p 80:6000 --name mysparkcontainer andresmarcelo7/sparkwebapprepo:latest\n";
        engineEC2Instance.addUserData(userData);

        return engineEC2Instance;
    }
}
