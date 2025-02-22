package com.sgi.card.infrastructure.controller;

import com.sgi.card.domain.ports.in.CardService;
import com.sgi.card.infrastructure.dto.AssociateRequest;
import com.sgi.card.infrastructure.dto.CardRequest;
import com.sgi.card.infrastructure.dto.CardResponse;
import com.sgi.card.infrastructure.dto.TransactionResponse;
import com.sgi.card.infrastructure.dto.BalanceResponse;
import com.sgi.card.infrastructure.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CardController implements V1Api {

    private final CardService cardService;

    @Override
    public Mono<ResponseEntity<CardResponse>> associateDebitCardToAccount(String cardId, Mono<AssociateRequest> associateRequest, ServerWebExchange exchange) {
        return cardService.associateDebitCardToAccount(cardId, associateRequest)
                .map(cardResponse -> ResponseEntity.status(HttpStatus.OK)
                        .body(cardResponse));
    }

    @Override
    public Mono<ResponseEntity<CardResponse>> createCard(Mono<CardRequest> cardRequest, ServerWebExchange exchange) {
        return cardService.createCard(cardRequest)
               .map(cardResponse -> ResponseEntity.status(HttpStatus.CREATED)
                       .body(cardResponse));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCard(String cardId, ServerWebExchange exchange) {
        return cardService.deleteCard(cardId)
                .map(cardResponse -> ResponseEntity.ok().body(cardResponse));
    }

    @Override
    public Mono<ResponseEntity<Flux<CardResponse>>> getAllCards(String clientId, String type, String cardId, ServerWebExchange exchange) {
        return Mono.fromSupplier(() -> ResponseEntity.ok().body(cardService.getAllCards(clientId, type, cardId)));
    }

    @Override
    public Mono<ResponseEntity<CardResponse>> getCardById(String cardId, ServerWebExchange exchange) {
        return cardService.getCardById(cardId)
                .map(cardResponse -> ResponseEntity.ok().body(cardResponse));
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionResponse>>> getLastTransactions(String cardId, Integer page, Integer size, ServerWebExchange exchange) {
        return Mono.fromSupplier(() -> ResponseEntity.ok().body(cardService.getLastTransactions(cardId, page, size)));
    }

    @Override
    public Mono<ResponseEntity<BalanceResponse>> getPrimaryAccountBalance(String cardId, ServerWebExchange exchange) {
        return cardService.getPrimaryAccountBalance(cardId)
                .map(cardResponse -> ResponseEntity.ok().body(cardResponse));
    }

    @Override
    public Mono<ResponseEntity<Object>> processPaymentOrWithdrawal(String cardId, Mono<PaymentRequest> paymentRequest, ServerWebExchange exchange) {
        return   cardService.processPaymentOrWithdrawal(cardId, paymentRequest)
                .thenReturn(ResponseEntity.ok().body(Map.of("message","Operation completed")));
    }

    @Override
    public Mono<ResponseEntity<CardResponse>> updateCard(String cardId, Mono<CardRequest> cardRequest, ServerWebExchange exchange) {
        return cardService.updateCard(cardId, cardRequest)
                .map(cardResponse -> ResponseEntity.ok().body(cardResponse));
    }
}
