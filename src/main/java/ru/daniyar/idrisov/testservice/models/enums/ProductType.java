package ru.daniyar.idrisov.testservice.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    DESKTOP_COMPUTER("Настольный компьютер"),
    LAPTOP("Ноутбук"),
    MONITOR("Монитор"),
    HDD("Жесткий диск");

    private final String description;

}
