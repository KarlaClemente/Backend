package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.api.entity.InvoiceItem;

@Repository
public interface RepoInvoiceItem extends JpaRepository<InvoiceItem, Integer> {
}
