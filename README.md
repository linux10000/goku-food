# Goku Food API

## Tarefas Restantes
- Logs
- Controle de Concorrencia
- Testes da camada de controller
- Tratar algumas mensagens de erro

## Executação

### Build e Run
O projeto está disponivel em docker. Portanto, basta buildar e rodar a imagem para executar o projeto.
A exposição da porta HTTP 8080 está declarada do Dockerfile.
```sh
docker build -t goku-api .
docker run -it --rm --network=host goku-api
```


### Outras Informações
Credenciais do usuario admin
```sh
login = 'admin'
senha = '12345678'
```

URL:
- OpenApi = http://localhost:8080/swagger-ui.html
- Info = http://localhost:8080/actuator/info
- Health = http://localhost:8080/actuator/health