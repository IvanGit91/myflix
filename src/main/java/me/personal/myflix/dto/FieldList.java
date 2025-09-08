package me.personal.myflix.dto;

import lombok.*;
import me.personal.myflix.entity.base.BaseEntity;
import me.personal.myflix.enums.ASSOCIATION_ENUM;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FieldList<T extends BaseEntity<?>> {
    private String fieldName;
    private String mappedBy;
    private List<T> list;
    private boolean idNull;
    private ASSOCIATION_ENUM association;
    private Object id;  // In genere serve le many to many con campi addizionali
    private String mapFieldName;  // In genere serve le many to many con campi addizionali
}
