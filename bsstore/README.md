# fdbstore

A Clojure library designed to ... well, that part is up to you.

## Usage

### generate protobuf->java

> :warning: clj task doesn't work due to optional fields

```shell
clj -T:proto generate
clj -T:proto generate-and-compile
```

This works
```shell
docker pull registry.internal.exoscale.ch/exoscale/buf
docker run --rm -v `pwd`/proto:/defs registry.internal.exoscale.ch/exoscale/buf generate
```

## TODO

- [ ] validate schema
- [ ] find a way to reference `blob.prot`?