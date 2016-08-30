# armeria-sample

armeria を利用したアプリケーション実装例。

以下のサービスを適用済み

 * gRPC を利用したサービス
 * thrift を利用したサービス

# setup

    brew install thrift
    npm install -g grpc

# gradle タスク

protobuf の再生成

    ./gradlew generateproto

thrift の再生成

    ./gradlew compileThrift

# Endpoints

 * http://localhost:8080/hello
 * http://localhost:8080/docs/
 * http://localhost:8080/static-cp/hoge.txt
 * http://localhost:8080/static-fs/hoge.txt

# Sample API call(thrift)

    curl 'http://localhost:8080/hello' \
        -H 'Content-Type: application/x-thrift; protocol=TTEXT' \
        --data-binary '{"method":"hello","type":"CALL","args":{"name":"hoge"}}'

# gRPC サポートについて

armeria は gRPC をサポートしているが、これは armeria のサーバーが servlet をホスティングできるのと同程度の意味でサポートしているにすぎない。

armeria ユーザーが grpc を利用することのメリットとして以下のようなことがある

 * Full support for request/response streaming
 * Protocol buffer generated code can be a nice change
 * Efficient clients on a variety of platforms (Android, iOS, Go, etc)
 
grpc ユーザーが armeria を利用するメリットには以下のようなメリットがある

 * Support for HTTP1 (not verified, will probably require some followup work). HTTP1 should open GRPC to the browser and work better with Cloud load balancers that generally translate HTTP2 -> HTTP1
 * Once implemented, DocService and Grpc on the same server
 * And any other servers they feel like having on the same server since armeria's flexible that way :)

(現状では DocService は grpc に対応してない気がする)

https://github.com/line/armeria/pull/247 を詳しくは参照のこと

gRPC を armeria 上で利用した場合、JSON でのアクセスなどが困難なので普段使うには thrift のほうがいいかなあ。

