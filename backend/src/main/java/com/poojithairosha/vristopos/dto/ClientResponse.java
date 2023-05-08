package com.poojithairosha.vristopos.dto;

import lombok.Builder;

@Builder
public record ClientResponse(boolean success, Object message) {
}
