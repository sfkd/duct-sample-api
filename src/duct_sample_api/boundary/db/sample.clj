(ns duct-sample-api.boundary.db.sample
  (:require [duct.database.sql]
            [duct-sample-api.boundary.db.core :as db]
            [honeysql.core :as sql]))

(defprotocol Sample
  (list-samples [db])
  (create-sample [db sample])
  (fetch-sample [db id]))

(extend-protocol Sample
  duct.database.sql.Boundary
  (list-samples [db]
    (db/select db (sql/build {:select :*
                              :from :sample})))
  (create-sample [db sample]
    (db/insert! db :sample sample))
  (fetch-sample [db id]
    (db/select-one db (sql/build {:select :*
                                  :from :sample
                                  :where [:= :id id]}))))
