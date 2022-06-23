(ns exoscale.entity.blockstorage)

(defprotocol AtomicMetastore
  "The main protocol the underlying transactables should implement"
  (-run-in-transaction
    [this f]
    "Run `f` over an implementation of all known metadata protocols:
     - `extent/ExtentStore`")
  (-long-range-reduce [this f val record-type items]
    "Long range reducer")
  (-long-query-reduce
    [this f val query]
    [this f val query filter]
    [this f val query filter opts]
    "Runs a long query reducer via `f` with initial val `val`, with optional query `filter` and `opts`"))

(defmacro with-transaction
  "Perform provided in the context of a transaction against the store"
  [[sym store] & fntail]
  `(-run-in-transaction ~store (fn [~sym] ~@fntail)))
