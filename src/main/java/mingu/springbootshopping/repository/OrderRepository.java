package mingu.springbootshopping.repository;

import mingu.springbootshopping.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
