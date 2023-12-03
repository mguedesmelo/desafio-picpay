# Simple Picpay

Documentar serviços com swagger
Documentar padrões adotados (PSRs, design patterns, SOLID)
Testar o tratamento de erros
Habilitar autenticação e validação via JWT



# Desafio PicPay
https://github.com/PicPay/picpay-desafio-backend

Criar aplicação que exponha uma API RESTful para cadastrar 2 tipos de usuários (clientes e lojistas).
Ambos têm carteira com dinheiro e realizam transferências entre eles.

<div style="display: inline_block"><br>
  <img align="center" alt="mguedesmelo-java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg">
  <img align="center" alt="mguedesmelo-angular" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/angularjs/angularjs-plain.svg">
  <img align="center" alt="mguedesmelo-Js" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-plain.svg">
  <img align="center" alt="mguedesmelo-Ts" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/typescript/typescript-plain.svg">
  <img align="center" alt="mguedesmelo-HTML" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original.svg">
  <img align="center" alt="mguedesmelo-CSS" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original.svg">
</div>

## 💻 Tecnologias
* [Java](https://www.java.com/) - Linguagem Backend
* [Spring](https://spring.io/) - Framework Web Java
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework Initializer
* [Hibernate](http://hibernate.org/orm/) - ORM
* [Tomcat](http://tomcat.apache.org/) - Servlet Container
* [Swagger](https://swagger.io/) - Gerenciador de Documentação e Testes Funcionais
* [H2 Database](http://www.h2database.com) - SGBD
* [Spring Tools 4 for Eclipse](https://spring.io/tools) - IDE

## ⌨️ Instalação
1. Clone o repositório para baixar as pastas contendo o código fonte dos projeto Java e Angular.

```
git clone https://github.com/mguedesmelo/simplepicpay.git
```
2. Importe o projeto "simplepicpay" no Eclipse STS como um projeto maven e execute como uma aplicação Spring Boot


## ✅Atributos das Classes

**Usuário**
| Nome | Tipo | Descrição |
| ------ | ------ | ------ |
| firstName | String | Primeiro nome do usuário |
| lastName | String | Último nome do usuário |
| email | String | E-mail do usuário |
| password | String | Senha do usuário |
| balance | BigDecimal | Saldo do usuário |
| document | String | CPF/CNPJ do usuário |
| userType | UserType | Tipo do usuário, pode ser CUSTOMER ou MERCHANT |
| userRole | UserRole | Perfil do usuário, pode ser ADMIN ou USER |

**Transação**
| Nome | Tipo | Descrição |
| ------ | ------ | ------ |
| payer | User | Usuário que está realizando a transferência |
| payee | User | Usuário que irá receber o valor transferido |
| ammount | BigDecimal | Valor da transação |

## 🚧 Rotas

**Rotas que **NÃO EXIGEM** autenticação**

| Rota | Descrição | Tipo | Erros possíveis | Status |
| ------ | ------ | ------ | ------ | ------ |
| /api/login | Espera um objeto com os campos login e password para efetua o login. Deve ser retornado o token de acesso da API (JWT) com as informações do usuário logado | POST | 1 | Finalizado |
| /api/user | Espera um objeto com os campos necessários para a criação de um usuário. Deve ser retornado as informações do usuário criado | POST | 2,3,4,5 | Não iniciado |

**Erros possíveis:**
1. Login inexistente ou senha inválida: retornar um erro com a mensagem “Invalid login or password”;
2. E-mail já existente: retornar um erro com a mensagem “Email already exists”;
3. Login já existente: retornar um erro com a mensagem “Login already exists”;
4. Campos inválidos: retornar um erro com a mensagem “Invalid fields”;
5. Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.

**Exemplo de JSON para criação do usuário:**
```
{
  "firstName": "Hello",
  "lastName": "World",
  "email": "hello@world.com",
  "password": "h3ll0",
  "balance": "1200"
  "document": "00000000000",
  "userType": "CUSTOMER",
  "userRole": "USER"
}
```

**Rotas que **EXIGEM** autenticação**

Todas estas rotas esperam que seja enviado um token no cabeçalho da requisição.

| Rota | Descrição | Tipo | Erros possíveis | Status | 
| ------ | ------ | ------ | ------ | ------ |
| /api/me | Retorna as informações do usuário logado | GET | 1,2 | Não iniciado |
| /api/transfer | Listar todos os carros do usuário logado | GET | 1,2 | Não iniciado |

**Erros possíveis:**
1. Token não enviado: retornar um erro com a mensagem “Unauthorized”;
2. Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”;
3. Campos inválidos: retornar um erro com a mensagem “Invalid fields”;
4. Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.


**Exemplo de JSON para realização de transferência:**
```
{
  "payer": "1",
  "payee": "2",
  "ammount": "150"
}
```

## Para mais detalhes e efetuar os testes, basta executar o Swagger do projeto pelo endereço:
```
http://localhost:8080/swagger-ui.html
```


# ESTÓRIAS DE USUÁRIO

Objetivo da Sprint: Desenvolver funcionalidades básicas do backend.

**1. História de Usuário 1: Login**
- **Eu como** usuário do sistema
- **Quero** fazer login na API com meu login e senha
- **Para que** eu possa acessar os recursos protegidos da API

*Critérios de Aceitação:*
- A API deve fornecer um endpoint de login para autenticar usuários.
- Deve ser possível enviar uma solicitação para o endpoint de signin com um login e senha válidos.
- Em caso de login bem-sucedido, a API deve retornar um token de autenticação.
- Em caso de falha no login, a API deve retornar uma mensagem de erro.

**2. História de Usuário 2: Listar Usuários**
- **Eu como** usuário do sistema
- **Quero** listar todos os usuários cadastrados
- **Para que** eu possa visualizar informações sobre os usuários na API

*Critérios de Aceitação:*
- A API deve fornecer um endpoint para listar todos os usuários cadastrados.
- A lista de usuários deve ser retornada como uma resposta JSON.
- Os usuários devem ser retornados como objetos JSON com informações como nome, sobrenome, e-mail, etc.

**3. História de Usuário 3: Cadastrar Novo Usuário**
- **Eu como** usuário do sistema
- **Quero** cadastrar um novo usuário na API informando seus dados
- **Para que** o novo usuário tenha acesso à API

*Critérios de Aceitação:*
- A API deve fornecer um endpoint para cadastrar um novo usuário.
- Deve ser possível enviar uma solicitação para o endpoint de cadastro com os dados do novo usuário em formato JSON.
- A API deve validar os dados inseridos e retornar uma resposta com status apropriado.
- Em caso de falha no cadastro, a API deve retornar uma mensagem de erro.

**4. História de Transação 4: Realizar Transferência**
- **Eu como** usuário do sistema
- **Quero** transferir um valor para outro usuário
- **Para que** o valor seja debitado do meu saldo e creditado no saldo de outro usuário 

*Critérios de Aceitação:*
- A API deve fornecer um endpoint para realizar a transferência.
- Deve ser possível enviar uma solicitação para o endpoint com o ID dos dois usuários e o valor a ser transferido.
- Se os usuários forem encontrados e a transferência realizada com sucesso, a API deve retornar os detalhes da transação em formato JSON.
- Se nenhum usuário for encontrado com o ID fornecido, a API deve retornar um status apropriado.


# SOLUÇÃO

A escolha da tecnologia Java para o desenvolvimento de um sistema foi baseada em diversos fatores técnicos que podem influenciar no desempenho, manutenção, escalabilidade e sucesso do projeto. Vamos justificar essa decisão tecnicamente:

**Java no Backend:**

1. **Ampla Comunidade e Ecossistema:** Java é uma das linguagens de programação mais populares e amplamente adotadas no mundo. Isso resulta em uma grande comunidade de desenvolvedores, vasta documentação e uma ampla gama de bibliotecas e frameworks disponíveis. Essa riqueza de recursos facilita o desenvolvimento e a solução de problemas.

2. **Robustez e Confiabilidade:** Java é conhecido por sua robustez e confiabilidade. Ele é altamente tolerante a falhas e oferece um ambiente de execução seguro. Essas características são essenciais para sistemas críticos que precisam estar sempre disponíveis e funcionando corretamente.

3. **Desempenho:** Java possui um bom desempenho, principalmente quando combinado com otimizações de JVM (Java Virtual Machine). A capacidade de escalabilidade vertical e horizontal também é uma vantagem, permitindo que a aplicação cresça com o aumento da demanda.

4. **Segurança:** Java oferece recursos de segurança avançados, como controle de acesso, autenticação e autorização. Isso é fundamental para proteger os dados e garantir a conformidade com regulamentações de segurança.

5. **Integração:** Java suporta integração fácil com outros sistemas e serviços, tornando-o uma escolha sólida para aplicativos empresariais que precisam se comunicar com sistemas legados ou outros serviços web.

Em resumo, a escolha de Java no backend oferece um ambiente técnico robusto e escalável para o desenvolvimento de aplicações web por ser uma linguagem madura e segura. As tecnologias adotadas trabalham bem juntas e podem resultar em uma experiência de desenvolvimento eficiente e um sistema web confiável.


# BACKLOG

**Backend**
- [x] /api/login (GET) - Login de acesso
- [ ] Testes unitários de autenticação
- [ ] Testes unitários do controlador de usuários
