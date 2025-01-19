package org.example.portfolio_tracker.repo;

import org.example.portfolio_tracker.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository <Stock, Long>{
}
