package com.example.grpcserverdb;

import com.example.grpcserverdb.service.PersonGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class GrpcServerDbApplication {
    private static final Logger LOGGER = Logger.getLogger(GrpcServerDbApplication.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8081).addService(new PersonGrpcService()).build();
        server.start();
        LOGGER.info("Server started");
        server.awaitTermination();
    }



}
