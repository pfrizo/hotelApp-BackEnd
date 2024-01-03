# HotelApp Back-end
Uma api com banco local simples para suportar o sistema de cadastro de reservas de hotel, construído com Spring Boot, JDBC e H2 Database.

## Descrição
Este projeto consiste na implementação de uma API para o sistema de cadastro de reservas de hotel. 
Ela oferece endpoints para lidar com operações de criação, leitura, atualização e exclusão de reservas e usuários. O backend se integra com o frontend por meio de uma API RESTful.

Lembrando que o front-end da aplicação está disponível no repositório [hotelApp-FrontEnd](https://github.com/pfrizo/hotelApp-FrontEnd).

## Tecnologias Utilizadas
- Java Spring Boot
- JDBC
- H2 Database

## Como Usar
1. Clone este repositório e o repositório que contém o Front-end da aplicação.
2. Certifique-se de ter o Java e o Maven instalados. Execute esta aplicação Back-End
3. Abra o arquivo index.html em seu navegador.
4. Acesse a tela de login, clicando em "Entrar". Em seguida, por se tratar da primeira interação, clique em "Registrar" para criar seu usuário.
5. Para se registrar na plataforma, preencha o formulário e clique em "Registrar". (O Registro de usuário tem uma validação de e-mail e cpf, portanto utilize dados válidos e não repetidos).
6. Você será redirecionado para a tela de login. Preencha o formulário com e-mail e senha cadastrados anteriormente e clique em "Entrar".
7. Você será redirecionado para a tela inicial. Nela há as informações completas sobre os quartos. Clique no botão de "Reserve Agora" para acessar o form da reserva.
8. Preencha o formulário adequadamente e clique em "Calcular Preço" para que o valor da reserva seja calculado e apresentado.
9. Em seguida, clique em "Registrar" para cadastrar a reserva.
10. Para voltar ao menu, clique em "Hotel".
11. Para alterar os dados do usuário, clique em "Olá, { nome do usuário }". Você será redirecionado para um formulário com os dados do usuário. Altere os dados que achar necessário e clique em "Atualizar" e em "Ok" na janela de confirmação. Depois, volte ao menu clicando em "Voltar ao Menu"
12. Clique em "Minhas Reservas" na barra de navegação para ver todas as reservas feitas pelo seu usuário.
13. Para editar uma reserva, clique no ícone do lápis atrelado à reserva. Execute o mesmo procedimento utilizado para o registro para a alteração da reserva. Para excluir uma reserva, basta clicar no "X" e em "Ok" na janela de confirmação.
14. Para fazer o logout do usuário, basta clicar em "Sair" no menu principal e clicar em "Ok" na janela de confirmação.

### Visão de administrador
15. Acesse o arquivo management-index.html.
16. Para acessar a lista com todos os usuários cadastrados, clique em "Usuários". Para acessar a lista com todos os quartos cadastrados, clique em "Quartos". Para acessar o menu de reservas, clique em "Reservas".
17. Com o menu de Reservas aberto, é possível registrar uma nova reserva clicando em "Registrar" e preenchendo o formulário. É possível também listar todas as reservas cadastradas e alterá-las ou excluí-las clicando em "Reservas".

### Acesso ao Banco de Dados
18. Com a aplicação Back-End em execução, acesse [http://localhost:8090/h2-console](http://localhost:8090/h2-console)
19. Preencha o campo JDBC URL com "jdbc:h2:file:./hotelDB"; Usuário: adm; Senha: 123; e clique em "Connect".

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue para reportar bugs ou sugerir melhorias. Se desejar contribuir com código, faça um fork deste repositório e crie uma pull request com suas alterações.
