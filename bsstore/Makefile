grpc:
	#go install github.com/golang/mock/mockgen@latest
	docker pull registry.internal.exoscale.ch/exoscale/buf
	docker run --rm -v `pwd`/proto:/defs registry.internal.exoscale.ch/exoscale/buf generate
	#cd proto && rm -f rpc_grpc_mock.pb.go && mockgen -package=proto -self_package=github.com/exoscale/sos/proto -destination rpc_grpc_mock.pb.go . BlobStoreClient,BlobStoreServer,BlobStore_GetClient,BlobStore_GetPartialClient,BlobStore_GetPartialServer,BlobStore_GetServer,BlobStore_PutClient,BlobStore_PutServer
	#cd proto && go mod tidy

.PHONY: metastore all clean  grpc

define UNTIL
for i in `seq 1 ${2}`; do ${1} && break ; sleep 1; done;
endef

COMPOSE_FILE?=docker-compose.yaml:docker-compose.override.yaml
setup:
	COMPOSE_FILE=${COMPOSE_FILE} make -f proxy.mk compose-up

teardown:
	COMPOSE_FILE=${COMPOSE_FILE} make -f proxy.mk compose-down

wait-services:
	COMPOSE_FILE=${COMPOSE_FILE} make -f proxy.mk compose-wait

check-running:
	COMPOSE_FILE=${COMPOSE_FILE} make -f proxy.mk compose-check-running
