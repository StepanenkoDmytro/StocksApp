package com.stock.dto.portfolio.category;

import com.stock.dto.portfolio.spending.SpendingDTO;
import com.stock.model.portfolio.PortfolioCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String id;
    private String title;
    private String icon;
    private String parent;
    private boolean isSaved;

    public static PortfolioCategory mapFromDTO(CategoryDTO categoryDTO) {
        return new PortfolioCategory(
                categoryDTO.getId(),
                categoryDTO.getTitle(),
                categoryDTO.getIcon(),
                categoryDTO.getParent()
        );
    }

    public static CategoryDTO mapToDTO(PortfolioCategory category) {
        return new CategoryDTO(
                category.getId(),
                category.getTitle(),
                category.getIcon(),
                category.getParent(),
                true
        );
    }
}
