package com.mbit.pdf2image.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Timer {

    @NonNull
    private String name;

    private long init = System.currentTimeMillis();

    public void stop() {
        System.out.println(name + " took: " + new Double(System.currentTimeMillis() - init) / 1000);
    }
}
