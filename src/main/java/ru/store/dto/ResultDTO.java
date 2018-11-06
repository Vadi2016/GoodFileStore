package ru.store.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class ResultDTO {

    @Nullable
    protected Boolean success;

    @Nullable
    protected String message;

    public ResultDTO(@Nullable Boolean success) {
        this.success = success;
    }
}