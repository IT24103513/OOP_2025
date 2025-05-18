package org.parking.repositories;

import org.parking.models.Payment.Payment;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    void add(Payment p)throws IOException;
    List<Payment> findAll()throws IOException;

    Optional<Payment> find(long id)throws IOException;

    void update(Payment p)throws IOException;

    void delete(long id)throws IOException;
}
