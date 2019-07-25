package com.mbit.pdf2image.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Timer {

    @NonNull
    private String name;

    private long init = System.currentTimeMillis();

    public void stop() {
        log.debug(name + " took: " + new Double(System.currentTimeMillis() - init) / 1000);
    }
}
