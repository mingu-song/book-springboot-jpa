package mingu.springbootshopping.dto;

import lombok.Getter;
import lombok.Setter;
import mingu.springbootshopping.constant.ItemSellStatus;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";

}
