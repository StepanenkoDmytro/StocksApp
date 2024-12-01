package com.stock.dto.portfolio.category;

import com.stock.model.user.portfolio.entities.PortfolioCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String id;
    private String title;
    private String icon;
    private String color;
    private Double limit;
    private String parent;
    private boolean isSaved;

    public static PortfolioCategory mapFromDTO(CategoryDTO categoryDTO) {
        return new PortfolioCategory(
                categoryDTO.getId(),
                categoryDTO.getTitle(),
                categoryDTO.getIcon(),
                categoryDTO.getColor(),
                categoryDTO.getParent(),
                categoryDTO.getLimit()
        );
    }

    public static CategoryDTO mapToDTO(PortfolioCategory category) {
        return new CategoryDTO(
                category.getId(),
                category.getTitle(),
                category.getIcon(),
                category.getColor(),
                category.getLimit(),
                category.getParent(),
                true
        );
    }
}
