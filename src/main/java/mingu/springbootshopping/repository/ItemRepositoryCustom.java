package mingu.springbootshopping.repository;

import mingu.springbootshopping.dto.ItemSearchDto;
import mingu.springbootshopping.dto.MainItemDto;
import mingu.springbootshopping.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

//    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
