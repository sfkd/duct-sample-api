(ns duct-sample-api.boundary.db.core
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]))

(defn select [{db :spec} sql-map]
  (->> sql-map
       sql/format
       (jdbc/query db)))

(defn select-one [{db :spec} sql-map]
  (->> sql-map
       sql/format
       (jdbc/query db)
       first))

(defn insert! [{db :spec} table row]
  (->> row
       (jdbc/insert! db table)))
