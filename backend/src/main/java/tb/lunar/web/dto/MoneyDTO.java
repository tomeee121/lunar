package tb.lunar.web.dto;

import java.math.BigDecimal;

public record MoneyDTO(BigDecimal amount, String currency) {}
