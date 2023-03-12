package com.base;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private HttpClient client;
    @Autowired
    private KafkaTemplate<String, PaymentRequest> template;
    @Autowired
    private ServiceInstanceChooser instanceChooser;
    public TransactionController() {
        client = HttpClient.newBuilder().build();
    }


    @PostMapping("/process")
    public ResponseEntity<TransactionResponse> processTransaction(@RequestBody Transaction transaction) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        FraudRequest request = new FraudRequest(transaction.getTransactionId(), transaction.getIssuerId()
                ,transaction.getReceiverId(), transaction.getAmount());

        ServiceInstance instance = instanceChooser.choose("fraud-detector");
        if(Objects.isNull(instance)){
            return ResponseEntity.badRequest().body(new TransactionResponse("Transaction was not processed", "FAILED"));
        }
        System.out.println(instance);
        String url = String.format("%s://%s:%s/fraud-checker", instance.getScheme(),instance.getHost(), instance.getPort());

        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url))
                        .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request)))
                .header("Content-type", "application/json")
                        .build();

        HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if(httpResponse.statusCode() == 404){
            return ResponseEntity.badRequest().body(new TransactionResponse("Transaction was not processed", "FAILED"));
        }

        FraudResponse response = mapper.readValue(httpResponse.body(), FraudResponse.class);

        if(response.status().equals("valid")){
            System.out.println("Valid transaction");

            template.send("payments", transaction.getTransactionId(), new PaymentRequest(transaction.getIssuerAccountId(),
                    transaction.getReceiverAccountId(), transaction.getAmount()));

            return ResponseEntity.ok(new TransactionResponse("Transaction processed successfully", "SUCCESS"));
        }
        return ResponseEntity.ok(new TransactionResponse("Transaction was not processed it was deemed fraudulent", "FAILED"));


    }
}
