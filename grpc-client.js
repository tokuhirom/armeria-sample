const grpc = require('grpc');
const hi = grpc.load('src/main/proto/hi.proto').hi;
const service = new hi.Hi('127.0.0.1:8080', grpc.credentials.createInsecure());
service.hello("yay", function (err, feature) {
    console.log([err, feature]);
});
