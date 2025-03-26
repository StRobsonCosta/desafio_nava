package com.desafio.nava_log.infrastructure.aws;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.FargateService;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.s3.Bucket;

public class LogisticaStack extends Stack{

    public LogisticaStack(final Construct scope, final String id) {
        super(scope, id);

        Bucket logsBucket = Bucket.Builder.create(this, "LogisticaLogs")
                .versioned(true)
                .build();

        DatabaseInstance database = DatabaseInstance.Builder.create(this, "LogisticaDB")
                .engine(DatabaseInstanceEngine.POSTGRES)
                .instanceIdentifier("logistica-db")
                .build();

        Cluster cluster = Cluster.Builder.create(this, "LogisticaCluster").build();

//        FargateService.Builder.create(this, "LogisticaService")
//                .cluster(cluster)
//                .desiredCount(2)
//                .containerImage(ContainerImage.fromRegistry("amazon/amazon-ecs-sample"))
//                .build();
    }
}
