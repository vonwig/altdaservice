(defproject atomist/clj-test-docker "0.0.1-SNAPSHOT"

  :description "docker test"
  :url "https://github.com/atomisthq/bot-service"

  :dependencies [[ring/ring-core "1.8.2"]
                 [ring/ring-jetty-adapter "1.8.2"] [org.apache.commons/commons-text "1.5"]]

  :exclusions [commons-logging log4j org.slf4j/slf4j-log4j12]

  :min-lein-version "2.6.1"

  :source-paths ["src"]
  :test-paths ["test"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main atomist.main

  :jar-name "service.jar"

  :repositories [["releases" {:url "https://sforzando.jfrog.io/sforzando/libs-release-local"
                              :username [:gpg :env/mvn_artifactorymavenrepository_user]
                              :password [:gpg :env/mvn_artifactorymavenrepository_pwd]}]
                 ["plugins" {:url "https://sforzando.jfrog.io/sforzando/plugins-release"
                             :username [:gpg :env/mvn_artifactorymavenrepository_user]
                             :password [:gpg :env/mvn_artifactorymavenrepository_pwd]}]]

  :profiles {:metajar {:direct-linking true
                       :aot :all}

             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.4.0" :exclusions [cheshire ring/ring-codec]]
                                  [clj-http-fake "1.0.3" :exclusions [ring/ring-codec]]]
                   :plugins [[lein-metajar "0.1.1"]]}})
