# Reproducer project for netty-tcnative-boringssl crash on Alpine for aarch64 (ARM/Apple silicon)

This project reproduces a crash with `netty-tcnative-boringssl-static` up to `2.0.61.Final`.

## Reproducing the issue
On an Apple M1-based workstation, build the project and run the resulting docker container:

```shell
./gradlew dockerBuildImage
docker run netty-tcnative-crash-reproducer/app:latest
```

To generate and fetch a core dump and the error report file:

```shell
./gradlew dockerBuildImage
docker run --name reproducer --ulimit core=-1 netty-tcnative-crash-reproducer/app:latest
docker container cp reproducer:/app/hs_err_pid1.log ./
docker container cp reproducer:/app/core ./
docker container rm reproducer
```

