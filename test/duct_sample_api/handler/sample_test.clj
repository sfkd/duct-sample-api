(ns duct-sample-api.handler.sample-test
  (:require  [clojure.test :refer [deftest testing is]]
             [ring.mock.request :as mock]
             [duct.core :as duct]
             [integrant.core :as ig]
             dev))

(def profiles
  [:duct.profile/test])

(def config (duct/prep-config (dev/read-config) profiles))

(defn init-system []
  (ig/init config [:duct/migrator :duct.router/ataraxy]))

(defn http-get [router path]
  (-> (mock/request :get path)
      router))

(defn http-post [router path params]
  (-> (mock/request :post path)
      (assoc-in [:body-params] params)
      router))

(defn http-put [router path params]
  (-> (mock/request :put path)
      (assoc-in [:body-params] params)
      router))

(deftest sample-test
  (let [config (init-system)
        router (:duct.router/ataraxy config)]
    (testing "create new sample entry"
      (let [result (http-post router "/samples"
                              {:name "test"})]
        (is (= 200 (:status result)))))
    (testing "list sample entries"
      (let [result (http-get router "/samples")
            {samples :body} result]
        (is (= 200 (:status result)))
        (is (= 1 (count samples)))))
    (testing "get specific sample entry"
      (let [result (http-get router "/samples/1")
            {sample :body} result]
        (is (= 200 (:status result)))
        (is (= "test" (:name sample)))))))
