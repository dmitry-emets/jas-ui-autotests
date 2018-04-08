## UI autotests for JAS.
Simple UI autotests for JAS written in Kotlin using Appium automation tool.
As a result they generate [Allure](https://github.com/allure-framework/) report.

### How to start
```mvn clean test site -Dsurefire.suiteXmlFiles=<pathToSuiteXml> -Durl=<hubUrl>```  
where
* `<pathToSuiteXml>` - path to custom suite xml. Default is `data/suites/default.xml`
* `<hubUrl>` - Appium hub url, e.g. `http://127.0.0.1:4723/wd/hub`

### Allure report
Generated Allure report can be found at  
`target/site/allure-maven-plugin/index.html`.

### TODO
* Screenshots attachment on test fail.
* Custom error messages.
* Soft asserts.