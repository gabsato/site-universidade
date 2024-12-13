# Site Universidade

Este é um projeto de registro de estudantes para uma universidade, desenvolvido em Spring MVC.

## Funcionalidades

1. **Registrar alunos**: Os alunos podem se inscrever em um curso utilizando a página de registro de estudantes.
2. **Revisão dos dados**: Após o envio do formulário, as informações de registro são mostradas ao usuário para revisão.
3. **Confirmar e editar as informações**: Os alunos podem confirmar, editar ou cancelar o registro.
4. **Integração com API da ViaCEP**: Consome a API ViaCEP para recuperar a cidade baseada no CEP.
5. **Persistência em H2**: Salva as informações dos alunos no banco de dados em memória H2.

## Tecnologias Utilizadas

- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **Thymeleaf**
- **Bootstrap**
- **jQuery**
- **H2 Database**
- **Spring Cloud OpenFeign**

## Estrutura do Projeto

```bash
src
├── main
│   ├── java
│   │   └── acc/br/site_universidade
│   │       ├── controller
│   │       │   └── AlunoController.java
│   │       ├── model
│   │       │   └── Aluno.java
│   │       ├── repository
│   │       │   └── AlunoRepository.java
│   │       └── service
│   │           └── ViaCepService.java
│   └── resources
│       ├── templates
│       │   ├── index.html
│       │   ├── revisaoRegistro.html
│       │   ├── bemVindo.html
│       │   ├── editarAluno.html
│       │   └── studentRegistration.html
└── test
    └── java
        └── acc/br/site_universidade
            └── AlunoControllerTest.java
