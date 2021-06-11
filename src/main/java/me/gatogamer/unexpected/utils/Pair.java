package me.gatogamer.unexpected.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public final class Pair<X, Y> {

    private X x;
    private Y y;
}