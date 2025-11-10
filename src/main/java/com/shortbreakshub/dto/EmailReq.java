package com.shortbreakshub.dto;

public record EmailReq (
        String to,
        String displayName,
        String confirmUrl
        ){}
