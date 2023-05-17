package edu.miu.orderService.repositories;

import edu.miu.orderService.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel Tsegay Meresie
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
