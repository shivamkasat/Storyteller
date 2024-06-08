package com.storyteller.storyteller.utils;

import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageContent {
    private byte[] resource;
    private MediaType mediaType;
}
