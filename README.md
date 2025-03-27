
# Documentação da Aplicação - Desafio de Desenvolvimento

## Stacks Utilizadas
3. **Acessar os endpoints**:
    - **Java 17**
    - **Spring 3.4.4**
    - **Postgres**: _Persistência das Entidades_
    - **MongoDB**: _Gravar os Logs_
    - **WireMock**: _Simulação de Consulta de CEPs_
    - **Docker**
    - **JUnit 5 + Mockito**
    - **SWAGGER**

## Estrutura do Projeto

Esta aplicação foi desenvolvida como parte de um desafio e possui a seguinte estrutura de pacotes e classes.

### Pacotes e Classes

#### Pacote: `adapter`
- **Controller**
  - `FreteController`
  - `LogFreteController`
  - `TransportadoraController`

- **DTO**
  - `CoordenadaDto`
  - `EnderecoDto`
  - `FreteDto`
  - `TransportadoraDto`

#### Pacote: `application`
- **Port**
  - `CepCoordenadasUseCase`
  - `DistanciaUseCase`
  - `FreteUseCase`
  - `LogFreteUseCase`
  - `TransportadoraUseCase`

- **Out**
  - `CepApiClient`
  - `FreteRepository`
  - `LogFreteRepository`
  - `TransportadoraRepository`

- **Service**
  - `CepCoordenadasService`
  - `CepValidationService`
  - `DistanciaService`
  - `FreteCalculator`
  - `FreteService`
  - `LogFreteService`
  - `TransportadoraService`

#### Pacote: `domain`
- **Exception**
  - `CepNaoEncontradoException`
  - `FreteNaoEncontradoException`
  - `GlobalExceptionHandler`
  - `TransportadoraNaoEncontradaException`

- **Model**
  - `Frete`
  - `LogFrete`
  - `Transportadora`

#### Pacote: `infrastructure`
- **API**
  - `CepApiClientImpl`


- **Config**
  - `RestTemplateConfig`

- **Repository**
  - `FreteRepositoryImp`
  - `LogFreteRepositoryImp`
  - `TransportadoraRepositoryImp`

#### Pacote: `NavaLogApplication`

- `NavaLogApplication`

---

## Como Usar com Docker Compose

Para rodar a aplicação utilizando **Docker Compose**, você pode usar as seguintes stacks:

### Docker Compose Configuration

Crie um arquivo `docker-compose.yml` com as seguintes configurações:


### Passos para executar o Docker Compose:

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd <diretorio-do-repositorio>
   ```

2. **Suba os containers com o Docker Compose:**
   ```bash
   docker-compose up -d
   ```

3. **Verifique se os containers estão funcionando:**
   ```bash
   docker ps
   ```

### Acessando as APIs

- **PostgreSQL**: Porta 5432
- **MongoDB**: Porta 27017
- **WireMock**: Porta 8080

### Payloads de CEPs

- **test > Resources > __files**: _11 Modelos para Simulação de Consulta_
- **CEPs no Payload**:
  - 01001-000
  - 08753-360
  - 20040-020
  - 30130-010
  - 40020-010
  - 60025-000
  - 64000-080
  - 69005-000
  - 70710-907
  - 80010-000
  - 90010-150
  
---

## Como Executar a Aplicação Localmente

Para rodar a aplicação localmente, siga os passos abaixo:

1. **Instalar as dependências**:
   Se você estiver usando o Maven:
   ```bash
   mvn clean install
   ```

2. **Rodar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

3. **Acessar os endpoints**:
   - **Swagger UI**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
   - **API de Frete**: [POST /frete](http://localhost:8081/frete)
   - **API de LogFrete**: [POST /logfrete](http://localhost:8081/logfrete)
   - **API de Transportadora**: [POST /transportadora](http://localhost:8081/transportadora)

---

## Como Rodar os Testes

Os testes estão localizados no diretório `src/test/java`.

1. **Rodar todos os testes**:
   Se você estiver usando o Maven:
   ```bash
   mvn test
   ```

2. **Rodar testes específicos**:
   Para rodar os testes de uma classe específica, execute:
   ```bash
   mvn -Dtest=NomeDaClasseDeTeste test
   ```

---

## Notas Finais

Esta aplicação é estruturada para realizar operações de busca de cep em uma api externa (Definida aqui pela WireMock) pode ser facilmente configurada com **Docker** para uma execução isolada e em container.

Para a finalidade do Desafio, crie esta API que de acordo com o CEP de Destino, CEP de Origem e o parametro peso é definido qual transportadora será responsável pela entrega e qual custo do Frete. 

---

**Autor**: Robson
