(ns duct-sample-api.handler.sample
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [integrant.core :as ig]
            [duct-sample-api.boundary.db.sample :as db.sample]))


(defmethod ig/init-key :duct-sample-api.handler.sample/list [_ {:keys [db] :as opt}]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (db.sample/list-samples db)]))

(defmethod ig/init-key :duct-sample-api.handler.sample/create [_ {:keys [db] :as opt}]
  (fn [{[_ sample] :ataraxy/result}]
    [::response/ok (db.sample/create-sample db sample)]))

(defmethod ig/init-key :duct-sample-api.handler.sample/fetch [_ {:keys [db] :as opt}]
  (fn [{[_ id] :ataraxy/result}]
    [::response/ok (db.sample/fetch-sample db id)]))
