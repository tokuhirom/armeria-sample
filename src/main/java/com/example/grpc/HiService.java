package com.example.grpc;

import io.grpc.stub.StreamObserver;

public class HiService extends HiGrpc.HiImplBase {
    @Override
    public void hello(MyStringValue request, StreamObserver<MyStringValue> responseObserver) {
        responseObserver.onNext(MyStringValue.newBuilder()
                .setValue("Hi, " + request.getValue() + "!")
                .build());
        responseObserver.onCompleted();
    }
}
