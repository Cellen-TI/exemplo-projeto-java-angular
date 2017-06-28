Exemplo de projeto JAVA Cellen-TI

# backend

Feito com:
+ Java Spring Rest retornando JSon.
+ JPA e Spring Data.
+ Migration com FlyWay.

OBS: Nao versionar codigos compilados, temporarios ou especificos de alguma IDE. Por exemplo:
- a pasta build ou settings do eclipse.
- os arquivos .project ou .classpath do eclipse.
- o arquivo nb-configuration.xml do netbeans.
- a pasta target do maven.

Esses arquivos nao precisam serem versionados, e podem dar problema entre IDEs distintas nos desenvolvedores da equipe.

Usar encodding UTF-8 em todos os arquivos.

Para Executar:

NetBeans:

+ Indique para a IDE abrir a pasta raiz do projeto (a que contem o arquivo pom.xml).
+ Clique com o botão direito e escolha "Limpar e Construir". Neste momento o maven irá através da internet aos seus repositórios baixar os jars.
+ Ao final com sucesso, peça para Executar com o Tomcat.


# frontend

Feito com:
+ AngularJS.

Configurar o ubuntu:

+ sudo apt-get install npm
+ sudo apt-get install nodejs-legacy

Instalar dependencias transientes:

+ sudo npm install -g node
+ sudo npm install -g grunt-cli

Na raiz do projeto:

+ Instalar dependencias do projeto: npm install
+ Rodar o servidor node: grunt
+ Acessar: http://localhost:9000/

Proxy com NPM:
+ npm config set proxy http://proxy.company.com:8080
+ npm config set https-proxy http://proxy.company.com:8080
