# Simulador de Pagamentos — Padrões de Projeto em Java

Projeto do desafio final do bootcamp **DIO + Itaú (Java e IA)**: um simulador de pagamentos em **Java puro** que aplica
**7 padrões de projeto** trabalhando juntos em um fluxo realista.

## Fluxo

```
PaymentRequest
     │
     ▼
 PaymentFacade ──► Chain of Responsibility (antifraude)
     │                 ├─ BlockedPayerHandler  (lista negra)
     │                 ├─ AmountLimitHandler   (limite de R$ 10.000)
     │                 └─ NightCardHandler     (cartão alto na madrugada)
     │
     ├──► Factory ──► Strategy (Pix | Cartão | Boleto)
     │                    └─► Decorator (cashback, taxa %, tarifa fixa)
     │
     └──► Observer ──► NotificationListener + AuditLogListener ──► AuditLog (Singleton)
```

## Padrões usados e onde

| Padrão | Onde | Por quê |
|---|---|---|
| **Strategy** | `strategy/PaymentStrategy` + `PixStrategy`, `CreditCardStrategy`, `BoletoStrategy` | Cada forma de pagamento tem regra própria; adicionar uma nova não mexe nas existentes. |
| **Factory Method** | `strategy/PaymentStrategyFactory` | Centraliza a escolha da estratégia a partir do `PaymentType`. |
| **Decorator** | `decorator/*` | Taxas e descontos são empilháveis sobre o valor base (`BaseAmount`). |
| **Chain of Responsibility** | `fraud/*` | Regras de antifraude em elos independentes e reordenáveis. |
| **Observer** | `observer/PaymentEventPublisher`, `PaymentListener` | Notificação e auditoria reagem ao pagamento sem acoplar ao fluxo principal. |
| **Singleton** | `observer/AuditLog` (enum) | Uma única instância do log de auditoria, thread-safe. |
| **Facade** | `facade/PaymentFacade` | Uma única porta de entrada que esconde toda a orquestração. |

## Regras do simulador

- **Pix:** 1% de cashback
- **Cartão:** taxa de 2,99%
- **Boleto:** tarifa fixa de R$ 3,50
- **Antifraude:** pagador bloqueado, valor acima de R$ 10.000, ou cartão acima de R$ 2.000 entre 00:00 e 05:00

## Como rodar (Windows PowerShell)

Requisitos: **JDK 17+** e **Maven**.

```powershell
mvn clean test
mvn clean compile
java -cp target/classes br.com.dio.pagamentos.Main
```

## Estrutura

```
src/main/java/br/com/dio/pagamentos
├── Main.java
├── model/       PaymentRequest, PaymentResult, PaymentType, PaymentStatus
├── strategy/    Strategy + Factory
├── decorator/   Decorator
├── fraud/       Chain of Responsibility
├── observer/    Observer + Singleton
└── facade/      Facade
src/test/java/...   testes JUnit 5 para cada padrão
```

## Ideias para evoluir

- Trocar o `main` por uma API REST com Spring Boot (a `PaymentFacade` vira o service).
- Persistir o `AuditLog` em banco de dados.
- Adicionar novas formas de pagamento (ex.: carteira digital) só criando uma nova `PaymentStrategy`.
- Parcelamento no cartão com mais um `Decorator`.
