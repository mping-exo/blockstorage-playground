(def grpc-version "1.45.1")
(def netty-version "4.1.72.Final")
(def jackson-version "2.12.0")

(defproject bsstore "0.1.0-SNAPSHOT"
  :description "TODO: use tools.deps"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.google.protobuf/protobuf-java "3.19.1"]
                 [com.exoscale/sos-blobstore "0.9.303" :exclusions [io.grpc/grpc-core com.google.guava/guava]]
                 [com.exoscale/sos-protobuf "0.9.303" :exclusions [com.google.guava/guava io.grpc/grpc-api]]
                 [com.exoscale/sos-model "0.9.303"]
                 [com.exoscale/vinyl "0.1.8"]
                 [com.stuartsierra/component                 "1.1.0"]

                 [exoscale/telemetric-clj                    "0.1.29" :exclusions [com.google.guava/guava
                                                                                   com.google.errorprone/error_prone_annotations
                                                                                   com.google.j2objc/j2objc-annotations
                                                                                   org.codehaus.mojo/animal-sniffer-annotations
                                                                                   io.grpc/grpc-core]]
                 ;; pin versions to avoid issues
                 [org.slf4j/slf4j-api                        "2.0.0-alpha7"]
                 [org.slf4j/slf4j-simple                     "2.0.0-alpha7"]
                 [spootnik/unilog                            "0.7.28" :exclusions [ch.qos.logback/logback-classic
                                                                                   ch.qos.logback/logback-core]]
                 [ch.qos.logback/logback-classic             "1.3.0-alpha10"]
                 [ch.qos.logback/logback-core                "1.3.0-alpha10"]

                 [org.foundationdb/fdb-record-layer-core-pb3 "2.8.110.0"
                  :exclusions [org.codehaus.mojo/animal-sniffer-annotations]]

                 [io.grpc/grpc-netty                         ~grpc-version :exclusions [com.google.guava/guava io.grpc/grpc-api io.grpc/grpc-core]]
                 [io.grpc/grpc-core                          ~grpc-version :exclusions [com.google.guava/guava io.grpc/grpc-api]]
                 [io.grpc/grpc-api                           ~grpc-version :exclusions [com.google.guava/guava io.grpc/grpc-api]]
                 [io.grpc/grpc-protobuf                      ~grpc-version :exclusions [com.google.guava/guava io.grpc/grpc-api]]
                 [io.grpc/grpc-stub                          ~grpc-version :exclusions [com.google.guava/guava io.grpc/grpc-api]]

                 [com.google.protobuf/protobuf-java "3.19.2"]
                 [com.google.j2objc/j2objc-annotations "1.3"]
                 [org.checkerframework/checker-qual "3.12.0"]

                 [io.netty/netty-all                         ~netty-version]
                 [io.netty/netty-transport-native-epoll      ~netty-version]
                 [io.netty/netty-handler-proxy               ~netty-version]
                 [io.netty/netty-handler                     ~netty-version]
                 [io.netty/netty-codec                       ~netty-version]
                 [io.netty/netty-codec-socks                 ~netty-version]
                 [io.netty/netty-codec-http                  ~netty-version]
                 [io.netty/netty-codec-http2                 ~netty-version]
                 [io.netty/netty-buffer                      ~netty-version]
                 [io.netty/netty-resolver                    ~netty-version]
                 [io.netty/netty-transport                   ~netty-version]
                 [com.fasterxml.jackson.core/jackson-databind ~jackson-version]
                 [com.fasterxml.jackson.core/jackson-core ~jackson-version]
                 [com.fasterxml.jackson.datatype/jackson-datatype-jsr310 ~jackson-version]
                 [com.fasterxml.jackson.core/jackson-annotations ~jackson-version]]
  :repositories {"exoscale" {:url "https://artifacts.exoscale.ch"}}
  :repl-options {:init-ns bsstore.core}
  :aliases {"compile" ["do" "javac," "compile," "check"]}
  :pedantic? :abort
  :java-source-paths ["proto/src"])