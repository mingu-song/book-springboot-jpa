package mingu.springbootshopping.repository;


import mingu.springbootshopping.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
