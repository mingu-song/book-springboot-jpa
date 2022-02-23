package mingu.springbootshopping.repository;


import mingu.springbootshopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
