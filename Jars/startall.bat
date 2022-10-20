set java8="<PATH TO JAVA8 .exe>"

start "cashbox" %java8% -jar CashBox.jar
start "productcatalog" %java8% -jar ProductCatalog.jar
start "customerRegister" %java8% -jar CustomerRegister.jar
start "cardReader" %java8% -jar CardReader.jar
