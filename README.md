# Sistema de Pedidos
Projeto pessoal desenvolvido nos padrões REST em Java e Spring Boot, com o objetivo de simular um e-commerce.

## 🛠 Ferramentas e Técnicas Utilizadas
- Java (17)
- Spring Boot (3.5.8)
- Programação Orientada a Objetos
- Arquitetura em camadas (Controller, Service, Repository, Entity, DTO)
- Modelagem UML
- Spring Data JPA
- Spring Web
- Spring Security + JWT
- PostegreSQL
- Maven
- Postman
- Swagger Open AI
- Busca paginada

-------------------------------------------------------------------------------------------------------------

## Instalação
1. Clone o repositório: `git clone https://github.com/ViniciusCarnot/sistema-de-pedidos.git`
2. Instale o PostgreSQL.

-------------------------------------------------------------------------------------------------------------

## Uso
1. Abra o projeto clonado em sua IDE de preferência.
2. Inicie a API na classe `SistemaDePedidosApplication`.
3. A API estará disponível em http://localhost:8081
4. A interface do Swagger estará disponível em http://localhost:8081/swagger-ui/index.html#/
5. Acessar o arquivo `application.properites` para mais detalhes sobre o banco de dados.

-------------------------------------------------------------------------------------------------------------

## 📋 Funcionalidades
### Usuários não autenticados (sem login) 
- Realizar cadastro.
- Realizar login.
- Ler informações dos produtos do catálago.
- Ler informações das categorias do catálago.

### Usuários com permissão 'NORMAL' ou 'ADMIN'
- Atualizar dados da própria conta.
- Realizar pedidos.
- Cancelar próprios pedidos.
- Atualizar dados dos próprios pedidos.
- Ler informações dos próprios pedidos.

### Usuário APENAS com permissão 'ADMIN'
- Criar e inserir produtos no catálago.
- Deletar produtos.
- Atualizar produtos.
- Ler informações confidenciais dos produtos.
- Criar e inserir categorias no catálago.
- Inserir produtos nas categorias.
- Deletar categorias.
- Atualizar categorias.
- Ler informações confidenciais das categoria e de seus produtos.
- Cancelar qualquer pedido.
- Atualizar dados de qualquer pedido.
- Ler informações de qualquer pedido.

-------------------------------------------------------------------------------------------------------------

### Fotos do Projeto

<img width="1258" height="732" alt="Captura de Tela (1826)" src="https://github.com/user-attachments/assets/0d8b13d6-6bb0-42ec-b9a8-58fb12d1b3cb" />

-------------------------------------------------------------------------------------------------------------

<img width="2560" height="1080" alt="Captura de Tela (1827)" src="https://github.com/user-attachments/assets/571615bf-e747-4ed2-99c7-2fdfed8ef3a3" />

-------------------------------------------------------------------------------------------------------------

<img width="2560" height="1080" alt="Captura de Tela (1828)" src="https://github.com/user-attachments/assets/d2e51f9c-6281-4ef9-af8a-dbb1249d8237" />

-------------------------------------------------------------------------------------------------------------

<img width="2560" height="1080" alt="Captura de Tela (1829)" src="https://github.com/user-attachments/assets/39e68bb1-d3c7-445e-a2e7-9180c462ecb9" />

-------------------------------------------------------------------------------------------------------------

<img width="2560" height="1080" alt="Captura de Tela (1831)" src="https://github.com/user-attachments/assets/ff46623b-fe45-4de2-8bd2-3f8dac6c577c" />

-------------------------------------------------------------------------------------------------------------

<img width="2560" height="1080" alt="Captura de Tela (1832)" src="https://github.com/user-attachments/assets/c518f4b3-f5e4-4a44-aa01-09503fe70921" />

-------------------------------------------------------------------------------------------------------------

<img width="2560" height="1080" alt="Captura de Tela (1833)" src="https://github.com/user-attachments/assets/94ce875a-8ad5-4cf1-9d33-d77b26cd27ed" />

-------------------------------------------------------------------------------------------------------------

<img width="2044" height="949" alt="Captura de Tela (1834)" src="https://github.com/user-attachments/assets/32fa64f5-1f64-4016-a164-9975c1ca44ac" />

-------------------------------------------------------------------------------------------------------------

<img width="2027" height="967" alt="Captura de Tela (1835)" src="https://github.com/user-attachments/assets/79702590-ac27-4e32-b457-7c61ce12b946" />

-------------------------------------------------------------------------------------------------------------

<img width="2020" height="766" alt="Captura de Tela (1836)" src="https://github.com/user-attachments/assets/027e2ac3-72eb-428d-bf74-e1ffc86c9bfd" />
