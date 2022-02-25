package mingu.springbootshopping.controller;

import lombok.RequiredArgsConstructor;
import mingu.springbootshopping.dto.CartDetailDto;
import mingu.springbootshopping.dto.CartItemDto;
import mingu.springbootshopping.dto.CartOrderDto;
import mingu.springbootshopping.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity<?> cart(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(sb.toString());
        }

        String email = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(cartItemId);
    }

    @GetMapping(value = "/cart")
    public String cartHist(Principal principal, Model model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal) {

        if (count <= 0) {
            return ResponseEntity.badRequest().body("최소 1개 이상 담아주세요");
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        cartService.updateCartItemCount(cartItemId, count);
        return ResponseEntity.ok(cartItemId);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {

        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.ok(cartItemId);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal){

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("주문할 상품을 선택해주세요");
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if (!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("주문 권한이 없습니다.");
            }
        }

        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return ResponseEntity.ok(orderId);
    }

}
