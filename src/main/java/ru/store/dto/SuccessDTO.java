package ru.store.dto;

import org.jetbrains.annotations.Nullable;

public class SuccessDTO extends ResultDTO {


    public SuccessDTO(@Nullable Boolean success) {
        super(success);
    }
}